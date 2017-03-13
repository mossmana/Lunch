package com.killerandroid.lunchme.yelp;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class YelpSearchRequest {

    private static final String URL = "https://api.yelp.com/v3/businesses/search?term=restaurants&radius=805&open_now=true&latitude=%f&longitude=%f";

    private Response.Listener<String> listener;
    private String url;
    private YelpAuthToken token;

    public YelpSearchRequest(Location location, YelpAuthToken token, Response.Listener<String> listener) {
        this.url = String.format(Locale.getDefault(), URL, location.getLatitude(), location.getLongitude());
        this.token = token;
        this.listener = listener;
    }

    public void send(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, listener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token.getAccessToken());
                    return headers;
                }
            };
        queue.add(request);
    }
}
