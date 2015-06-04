package me.jp.volley.enhance;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jp.volley.common.App;
import me.jp.volley.common.Constants;
import me.jp.volley.utils.PreferencesUtils;

/**
 * 提取cookie并保存到本地
 *
 * @author ChenLong
 */
public class JsonObjectRequestWithCookie extends Request<JSONObject> {

    private Map<String, String> mMap;
    private Response.Listener<JSONObject> mListener;
    public String cookieFromResponse;
    private String mHeader;
    private Map<String, String> sendHeader = new HashMap<String, String>(1);
    private boolean mIsSaveCookie = false;

    public JsonObjectRequestWithCookie(int method, String urlStr,
                                       Response.Listener<JSONObject> listener,
                                       Response.ErrorListener errorListener, Map<String, String> params) {
        super(method, urlStr, errorListener);
        mListener = listener;
        mMap = params;
    }

    // 当http请求是post时，则需要该使用该函数设置往里面添加的键值对
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mMap.isEmpty()) {
            return super.getParams();
        }
        return mMap;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            if (mIsSaveCookie) {
                mHeader = response.headers.toString();
                // 使用正则表达式从reponse的头中提取cookie内容的子串
                Pattern pattern = Pattern.compile("Set-Cookie.*?;");
                Matcher m = pattern.matcher(mHeader);
                if (m.find()) {
                    cookieFromResponse = m.group();
                    // 去掉cookie末尾的分号
                    cookieFromResponse = cookieFromResponse.substring(11,
                            cookieFromResponse.length() - 1);
                    // LogUtil.i("getcookie:" + cookieFromResponse);
                    // 将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
                    // jsonObject.put("Cookie", cookieFromResponse);
                    // 将cookie保存到本地
                    PreferencesUtils.PREFERENCE_NAME = Constants.PREFS_USER_INFO;
                    PreferencesUtils.putString(App.getInstance()
                                    .getApplicationContext(), "cookie",
                            cookieFromResponse);
                }
            }
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }

    /**
     * 请求时添加cookie
     */
    public void sendCookie() {
        PreferencesUtils.PREFERENCE_NAME = Constants.PREFS_USER_INFO;
        String cookie = PreferencesUtils.getString(App.getInstance().getApplicationContext(), "cookie", "");
        // LogUtil.i("sendcookie:" + cookie);
        if ("".equals(cookie)) {
            return;
        }
        sendHeader.put("Cookie", cookie);
    }

    /**
     * true保存cookie,false不保存
     *
     * @param flag
     */
    public void saveCookie(boolean flag) {
        mIsSaveCookie = flag;
    }
}
