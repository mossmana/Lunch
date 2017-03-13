package com.killerandroid.lunchme.yelp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class YelpAuthTokenRequest {

    private static final String URL = "https://api.yelp.com/oauth2/token";
    private static final String GRANT_TYPE_KEY = "grant_type";
    private static final String GRANT_TYPE_VALUE = "client_credentials";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_ID_VALUE = "_PEKrcc_DFkrBCCWk-_IUA";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "YTjOT0gVb7twsnv1qkXUbRA8uRmtW1WhU00LcdL9c1ey9vuiPyxlCwZzwsJTUmDn";

    private Response.Listener<String> listener;

    public YelpAuthTokenRequest(Response.Listener<String> listener) {
        this.listener = listener;
    }

    public void send(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, URL, listener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(GRANT_TYPE_KEY, GRANT_TYPE_VALUE);
                    params.put(CLIENT_ID_KEY, CLIENT_ID_VALUE);
                    params.put(CLIENT_SECRET_KEY, CLIENT_SECRET_VALUE);
                    return params;
                }
            };
        queue.add(request);
    }
}
