package com.killerandroid.lunchme.yelp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 {
 "access_token": "5TGy8YZIBC0saYaqj_19Rc9OREB-9hMiv-19kO9HyUmQHudY3bCG5XRZ5PszDxC3THUzw0eai1-pXDTm23Nz72tDaxSadUAzGyy3HvtxSJbMinG6K9lvJj-JIaPEWHYx",
 "expires_in": 15551999,
 "token_type": "Bearer"
 }
 */
public class YelpAuthTokenResponse {

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("expires_in")
    public long expiresIn;
    @SerializedName("token_type")
    public String tokenType;

    public static YelpAuthTokenResponse parse(String response) {

        InputStream inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        return gson.fromJson(reader, YelpAuthTokenResponse.class);
    }
}
