/**
 * Copyright 2013 Mani Selvaraj
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.enhance;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * MultipartRequest - To handle the large file uploads. Extended from
 * JSONRequest. You might want to change to StringRequest based on your response
 * type.
 */
public class MultiPartJSONRequest extends JsonRequest<JSONObject> implements
        MultiPartRequest {

    private final Response.Listener<JSONObject> mListener;
    /* To hold the parameter name and the File to upload */
    private Map<String, File> fileUploads = new HashMap<String, File>();
    private Map<String, File[]> filesUploads = new HashMap<String, File[]>();

    /* To hold the parameter name and the string content to upload */
    private Map<String, String> stringUploads = new HashMap<String, String>();
    private Map<String, String> mHeader = new HashMap<String, String>(1);

    private Context mContext;

    /**
     * Creates a new request with the given method.
     *
     * @param method        the request {@link Method} to use
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public MultiPartJSONRequest(Context context, int method, String url, JSONObject jsonRequest,
                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest
                .toString(), listener, errorListener);

        mContext = context;
        mListener = listener;
    }

    @Override
    public void addFilesUpload(String param, File[] files) {
        filesUploads.put(param, files);
    }

    public void addFileUpload(String param, File file) {
        fileUploads.put(param, file);
    }

    public void addStringUpload(String param, String content) {
        stringUploads.put(param, content);
    }

    /**
     * upload files
     */
    public Map<String, File[]> getFilesUploads() {
        return filesUploads;
    }

    /**
     * upload files
     */
    public Map<String, File> getFileUploads() {
        return fileUploads;
    }

    /**
     * upload string params
     */
    public Map<String, String> getStringUploads() {
        return stringUploads;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String parsed = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(parsed),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));

        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

    /**
     * request with cookie
     */
    public void setCookie() {
        //read local cookie
        String cookie = PreferencesUtils.getString(mContext, "cookie", "");
        if (TextUtils.isEmpty(cookie)) {
            return;
        }
        // put into http header
        mHeader.put("Cookie", cookie);
    }

    /**
     * null means do not upload
     */
    public String getBodyContentType() {
        return null;
    }
}