package com.killerandroid.lunchme.yelp;

import android.content.SharedPreferences;

public class YelpAuthToken {

    private static final String PREFERENCE_ACCESS_TOKEN = "accessToken";
    private static final String PREFERENCE_CREATED_AT = "createdAt";
    private static final String PREFERENCE_EXPIRES_IN = "expiresIn";

    private SharedPreferences preferences;

    public YelpAuthToken(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCE_ACCESS_TOKEN, accessToken);
        editor.putLong(PREFERENCE_CREATED_AT, System.currentTimeMillis() / 1000);
        editor.commit();
    }

    public String getAccessToken() {
        return preferences.getString(PREFERENCE_ACCESS_TOKEN, null);
    }

    public long getCreatedAt() {
        return preferences.getLong(PREFERENCE_CREATED_AT, -1L);
    }

    public void setExpiresIn(long expiresIn) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREFERENCE_EXPIRES_IN, expiresIn);
        editor.commit();
    }

    public long getExpiresIn() {
        return preferences.getLong(PREFERENCE_EXPIRES_IN, -1L);
    }

    public boolean isExpired() {
        String accessToken = getAccessToken();
        long expiresIn = getExpiresIn();
        long createdAt = getCreatedAt();
        return accessToken == null || expiresIn == -1L || createdAt == -1L
                || (System.currentTimeMillis() / 1000) - createdAt <= 0;
    }
}