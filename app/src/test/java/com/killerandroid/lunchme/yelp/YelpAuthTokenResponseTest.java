package com.killerandroid.lunchme.yelp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class YelpAuthTokenResponseTest {

    @Test
    public void testParse() {
        String testResponse =
                "{\n" +
                " \"access_token\": \"5TGy8YZIBC0saYaqj_19Rc9OREB-9hMiv-19kO9HyUmQHudY3bCG5XRZ5PszDxC3THUzw0eai1-pXDTm23Nz72tDaxSadUAzGyy3HvtxSJbMinG6K9lvJj-JIaPEWHYx\",\n" +
                " \"expires_in\": 15551999,\n" +
                " \"token_type\": \"Bearer\"\n" +
                "}\n";

        YelpAuthTokenResponse response = YelpAuthTokenResponse.parse(testResponse);
        assertThat(response.accessToken, is("5TGy8YZIBC0saYaqj_19Rc9OREB-9hMiv-19kO9HyUmQHudY3bCG5XRZ5PszDxC3THUzw0eai1-pXDTm23Nz72tDaxSadUAzGyy3HvtxSJbMinG6K9lvJj-JIaPEWHYx"));
        assertThat(response.expiresIn, is(15551999L));
        assertThat(response.tokenType, is("Bearer"));
    }

}
