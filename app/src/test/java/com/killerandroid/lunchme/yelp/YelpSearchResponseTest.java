package com.killerandroid.lunchme.yelp;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class YelpSearchResponseTest {

    @Test
    public void testParse() {

        String testResponse = "{\n" +
                "  \"region\": {\n" +
                "    \"center\": {\n" +
                "      \"longitude\": -122.40698817253002,\n" +
                "      \"latitude\": 37.786882\n" +
                "    }\n" +
                "  },\n" +
                "  \"businesses\": [\n" +
                "    {\n" +
                "      \"review_count\": 918,\n" +
                "      \"price\": \"$$\",\n" +
                "      \"distance\": 1457.224941799,\n" +
                "      \"location\": {\n" +
                "        \"city\": \"San Francisco\",\n" +
                "        \"display_address\": [\n" +
                "          \"373 Columbus Ave\",\n" +
                "          \"San Francisco, CA 94133\"\n" +
                "        ],\n" +
                "        \"address1\": \"373 Columbus Ave\",\n" +
                "        \"address3\": \"\",\n" +
                "        \"state\": \"CA\",\n" +
                "        \"zip_code\": \"94133\",\n" +
                "        \"address2\": \"\",\n" +
                "        \"country\": \"US\"\n" +
                "      },\n" +
                "      \"is_closed\": false,\n" +
                "      \"phone\": \"+14154212337\",\n" +
                "      \"rating\": 4.5,\n" +
                "      \"name\": \"Molinari Delicatessen\",\n" +
                "      \"display_phone\": \"(415) 421-2337\",\n" +
                "      \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/H_vQ3ElMoQ8j1bKidrv_1w/o.jpg\",\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"title\": \"Delis\",\n" +
                "          \"alias\": \"delis\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"transactions\": [\n" +
                "        \"pickup\"\n" +
                "      ],\n" +
                "      \"id\": \"molinari-delicatessen-san-francisco\",\n" +
                "      \"url\": \"https://www.yelp.com/biz/molinari-delicatessen-san-francisco?adjust_creative=_PEKrcc_DFkrBCCWk-_IUA&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=_PEKrcc_DFkrBCCWk-_IUA\",\n" +
                "      \"coordinates\": {\n" +
                "        \"longitude\": -122.407821655273,\n" +
                "        \"latitude\": 37.7983818054199\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"review_count\": 1001,\n" +
                "      \"price\": \"$$\",\n" +
                "      \"distance\": 1197.6784884584,\n" +
                "      \"location\": {\n" +
                "        \"city\": \"San Francisco\",\n" +
                "        \"display_address\": [\n" +
                "          \"1058 Folsom St\",\n" +
                "          \"San Francisco, CA 94103\"\n" +
                "        ],\n" +
                "        \"address1\": \"1058 Folsom St\",\n" +
                "        \"address3\": \"\",\n" +
                "        \"state\": \"CA\",\n" +
                "        \"zip_code\": \"94103\",\n" +
                "        \"address2\": \"\",\n" +
                "        \"country\": \"US\"\n" +
                "      },\n" +
                "      \"is_closed\": false,\n" +
                "      \"phone\": \"+14155527687\",\n" +
                "      \"rating\": 4.5,\n" +
                "      \"name\": \"Deli Board\",\n" +
                "      \"display_phone\": \"(415) 552-7687\",\n" +
                "      \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/wA6jJVj5-by8NzVCCuBlmQ/o.jpg\",\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"title\": \"Delis\",\n" +
                "          \"alias\": \"delis\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"Sandwiches\",\n" +
                "          \"alias\": \"sandwiches\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"transactions\": [],\n" +
                "      \"id\": \"deli-board-san-francisco\",\n" +
                "      \"url\": \"https://www.yelp.com/biz/deli-board-san-francisco?adjust_creative=_PEKrcc_DFkrBCCWk-_IUA&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=_PEKrcc_DFkrBCCWk-_IUA\",\n" +
                "      \"coordinates\": {\n" +
                "        \"longitude\": -122.40709,\n" +
                "        \"latitude\": 37.7776799\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 2099\n" +
                "}";

        YelpSearchResponse response = YelpSearchResponse.parse(testResponse);
        assertThat(response.businesses, is(notNullValue()));
        assertThat(response.businesses.size(), is(2));
        YelpSearchResponse.Business business = response.businesses.get(0);
        assertThat(business.name, is("Molinari Delicatessen"));
        assertThat(business.coordinates, is(notNullValue()));
        assertThat(business.coordinates.longitude, is(-122.407821655273));
        assertThat(business.coordinates.latitude, is(37.7983818054199));
    }
}
