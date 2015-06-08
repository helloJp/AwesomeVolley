package me.jp.volley;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.enhance.JsonObjectRequestWithCookie;
import com.android.volley.enhance.MultiPartJSONRequest;
import com.android.volley.enhance.MultiPartStack;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void jsonRequestWithCookie() {
        String urlStr = ".....";
        JsonObjectRequestWithCookie jsonObjectRequest = new JsonObjectRequestWithCookie(this, Request.Method.GET, urlStr, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }, null);
        //catch Cookie from http head
        // jsonObjectRequest.saveCookie(true);
        jsonObjectRequest.sendCookie();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void batchUploadFiles() {
        String urlStr = "......";
        MultiPartJSONRequest multiPartRequest = new MultiPartJSONRequest(this, Request.Method.POST, urlStr, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            //upload multi files
            public Map<String, File[]> getFilesUploads() {
                Map<String, File[]> params = new HashMap<>();
                params.put("images[]", new File[]{new File("absolute file path"), new File("absolute file path")});
                return params;
            }

            //string params
            public Map<String, String> getStringUploads() {
                Map<String, String> params = new HashMap<>();
                params.put("params", "....");
                return params;
            }
        };
        multiPartRequest.setCookie();
        RequestQueue requestQueue = Volley.newRequestQueue(this, new MultiPartStack());
        requestQueue.add(multiPartRequest);
    }
}
