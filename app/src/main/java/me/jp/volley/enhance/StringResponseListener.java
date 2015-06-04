package me.jp.volley.enhance;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by JiangPing on 2015/5/18.
 */
public abstract class StringResponseListener {

    public Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            StringResponseListener.this.onResponse(response);
        }
    };
    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            StringResponseListener.this.onErrorResponse(error);
        }
    };

    public abstract void onResponse(String response);

    public abstract void onErrorResponse(VolleyError error);
}
