package com.killerandroid.lunchme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.killerandroid.lunchme.yelp.YelpAuthToken;
import com.killerandroid.lunchme.yelp.YelpAuthTokenRequest;
import com.killerandroid.lunchme.yelp.YelpAuthTokenResponse;
import com.killerandroid.lunchme.yelp.YelpSearchRequest;
import com.killerandroid.lunchme.yelp.YelpSearchResponse;

import java.util.Random;

public class LunchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 100;
    private static final String PREFERENCE_LAST_UPDATE = "lastUpdate";
    private static final String PREFERENCE_LAST_LATITUDE = "lastLatitude";
    private static final String PREFERENCE_LAST_LONGITUDE = "lastLongitude";
    private static final String PREFERENCE_LAST_NAME = "lastName";
    private static final String PREFERENCE_LAST_URL = "lastUrl";

    private GoogleMap mMap;
    private YelpAuthToken token;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        preferences = getSharedPreferences(
                getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String url = (String)marker.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        if (DateUtils.isToday(preferences.getLong(PREFERENCE_LAST_UPDATE,-1L))) {
            showMarker();
        } else {
            refresh();
        }
    }

    private void refresh() {
        mMap.clear();
        token = new YelpAuthToken(preferences);
        if (token.isExpired()) {
            YelpAuthTokenRequest request = new YelpAuthTokenRequest(new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    YelpAuthTokenResponse tokenResponse = YelpAuthTokenResponse.parse(response);
                    token.setAccessToken(tokenResponse.accessToken);
                    token.setExpiresIn(tokenResponse.expiresIn);
                    sendSearchRequest();
                }
            });
            request.send(this);
        } else {
            sendSearchRequest();
        }
    }

    private void sendSearchRequest() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_FINE_LOCATION);
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        YelpSearchRequest request = new YelpSearchRequest(location, token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                YelpSearchResponse searchResponse = YelpSearchResponse.parse(response);
                if (searchResponse.businesses == null)
                    return;
                int count = searchResponse.businesses.size();
                int random = new Random(System.currentTimeMillis()).nextInt(count);
                YelpSearchResponse.Business business = searchResponse.businesses.get(random);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(PREFERENCE_LAST_UPDATE, System.currentTimeMillis());
                editor.putFloat(PREFERENCE_LAST_LATITUDE, (float)business.coordinates.latitude);
                editor.putFloat(PREFERENCE_LAST_LONGITUDE, (float)business.coordinates.longitude);
                editor.putString(PREFERENCE_LAST_NAME, business.name);
                editor.putString(PREFERENCE_LAST_URL, business.url);
                editor.commit();
                showMarker();
            }
        });
        request.send(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSearchRequest();
                } else {
                    Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_lunch_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                // TODO: microtransaction
                refresh();
                break;
        }
        return true;
    }

    private void showMarker() {

        LatLng location = new LatLng(preferences.getFloat(PREFERENCE_LAST_LATITUDE, 0),
                preferences.getFloat(PREFERENCE_LAST_LONGITUDE, 0));
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(
                preferences.getString(PREFERENCE_LAST_NAME, null)
        ));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        marker.showInfoWindow();
        marker.setTag(preferences.getString(PREFERENCE_LAST_URL, null));
    }
}
