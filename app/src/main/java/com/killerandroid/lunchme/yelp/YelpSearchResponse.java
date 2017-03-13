package com.killerandroid.lunchme.yelp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
{
  "region": {
    "center": {
      "longitude": -122.40698817253002,
      "latitude": 37.786882
    }
  },
  "businesses": [
    {
      "review_count": 918,
      "price": "$$",
      "distance": 1457.224941799,
      "location": {
        "city": "San Francisco",
        "display_address": [
          "373 Columbus Ave",
          "San Francisco, CA 94133"
        ],
        "address1": "373 Columbus Ave",
        "address3": "",
        "state": "CA",
        "zip_code": "94133",
        "address2": "",
        "country": "US"
      },
      "is_closed": false,
      "phone": "+14154212337",
      "rating": 4.5,
      "name": "Molinari Delicatessen",
      "display_phone": "(415) 421-2337",
      "image_url": "https://s3-media1.fl.yelpcdn.com/bphoto/H_vQ3ElMoQ8j1bKidrv_1w/o.jpg",
      "categories": [
        {
          "title": "Delis",
          "alias": "delis"
        }
      ],
      "transactions": [
        "pickup"
      ],
      "id": "molinari-delicatessen-san-francisco",
      "url": "https://www.yelp.com/biz/molinari-delicatessen-san-francisco?adjust_creative=_PEKrcc_DFkrBCCWk-_IUA&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=_PEKrcc_DFkrBCCWk-_IUA",
      "coordinates": {
        "longitude": -122.407821655273,
        "latitude": 37.7983818054199
      }
    }
  ],
  "total": 2099
}
*/
public class YelpSearchResponse {

    public Region region;

    public static class Region {
        public Center center;
    }

    public static class Center {
        public double longitude;
        public double latitude;
    }

    public static class Location {
        public String city;
        @SerializedName("display_address")
        public ArrayList<String> displayAddress;
        public String address1;
        public String address2;
        public String address3;
        public String state;
        @SerializedName("zip_code")
        public String zipCode;
        public String country;
    }

    public static class Category  {
        public String title;
        public String alias;
    }

    public static class Coordinates {
        public double longitude;
        public double latitude;
    }

    public static class Business {
        @SerializedName("review_count")
        public int reviewCount;
        public String price;
        public double distance;
        @SerializedName("is_closed")
        public boolean isClosed;
        public String phone;
        public float rating;
        public String name;
        @SerializedName("display_phone")
        public String displayPhone;
        @SerializedName("image_url")
        public String imageUrl;
        public ArrayList<Category> categories;
        public ArrayList<String> transactions;
        public String id;
        public String url;
        public Coordinates coordinates;
    }

    public ArrayList<Business> businesses;

    public int total;

    public static YelpSearchResponse parse(String response) {

        InputStream inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        return gson.fromJson(reader, YelpSearchResponse.class);
    }
}
