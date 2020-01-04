package com.example.capstoneproject.utils;

import android.content.Context;

import com.example.capstoneproject.R;

public class ApiUtils {

    // Utils for API features
    public static final String SCHEME = "https";
    public static final String AUTHORITY = "maps.googleapis.com";
    public static final String PATH = "maps/api/place/textsearch/json";

    public static final String QUERY_KEY = "key";

    public static final String QUERY_TEXT = "query";

    private static final String QUERY_EUROPE_VALUE = "points+of+interest+in+Europe";
    private static final String QUERY_AFRICA_VALUE = "points+of+interest+in+Africa";
    private static final String QUERY_AMERICA_VALUE = "points+of+interest+in+America";
    private static final String QUERY_ASIA_VALUE = "points+of+interest+in+Asia";

    // Static method to get respective query for Continent string received and return it
    public static String getQueryValue(Context context, String query) {

        if (query.equals(context.getString(R.string.continent_europe))) {
            return QUERY_EUROPE_VALUE;
        } else if (query.equals(context.getString(R.string.continent_africa))) {
            return QUERY_AFRICA_VALUE;
        } else if (query.equals(context.getString(R.string.continent_america))) {
            return QUERY_AMERICA_VALUE;
        } else if (query.equals(context.getString(R.string.continent_asia))) {
            return QUERY_ASIA_VALUE;
        }

        return QUERY_EUROPE_VALUE;
    }


}
