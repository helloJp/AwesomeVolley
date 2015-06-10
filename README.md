# Volley
enhance volley with cookie &amp; upload files



### volley library has contained 3 http jar ( from http://hc.apache.org/ )
| Jar        |
| --------   |
| HttpClient 4.x |
| HttpClient 4.x |  
| HttpCore 4.x   |

Add Gradle dependency:
```gradle
dependencies {
   //wait.....

}
```

##1. JsonObjectRequestWithCookie can save or send cookie from http headers.
code is just like volley :
``` java
  String urlStr = ".....";
        JsonObjectRequestWithCookie jsonObjectRequest = new JsonObjectRequestWithCookie(getApplicationContext(), Request.Method.GET, urlStr, new Response.Listener<JSONObject>() {
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
```

the first param I use **"getApplicationContext()"** because of net request is out of UI thread. If you still use activity's context It may cause nullpointer ,when you are finish your activity request may still exist.

##2.upload files in same time
you can also put string params when you are uploading files .
``` java
String urlStr = "......";
        MultiPartJSONRequest multiPartRequest = new MultiPartJSONRequest(getApplicationContext(), Request.Method.POST, urlStr, null,
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
```
