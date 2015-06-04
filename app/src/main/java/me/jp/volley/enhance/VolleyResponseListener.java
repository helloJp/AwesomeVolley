package me.jp.volley.enhance;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by ${ChenShaoWu} on 2015/4/20.
 */
public abstract class VolleyResponseListener {

    public Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            VolleyResponseListener.this.onResponse(response);
        }
    };
    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyResponseListener.this.onErrorResponse(error);
        }
    };

    public abstract void onResponse(JSONObject response);

    public abstract void onErrorResponse(VolleyError error);




}
