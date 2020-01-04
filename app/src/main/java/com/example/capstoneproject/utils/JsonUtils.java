package com.example.capstoneproject.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.capstoneproject.objects.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Field tags needed to extract info from Json
    private static final String RESULTS_TAG = "results";
    private static final String PLACE_ID_TAG = "place_id";
    private static final String ICON_TAG = "icon";
    private static final String NAME_TAG = "name";
    private static final String ADDRESS_TAG = "formatted_address";
    private static final String OPENING_HOURS_TAG = "opening_hours";
    private static final String OPEN_NOW_TAG = "open_now";


    private static final String DATA_NOT_AVAILABLE = "Data not available";


    // Parse Json data and create Location item objects from that information

    static List<Location> extractLocationsDataFromJson(String jsonString) {

        // If Json string is empty or null, return null
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        List<Location> locations = new ArrayList<>();

        try {

            // Create Root Json Object
            JSONObject rootJsonResponse = new JSONObject(jsonString);

            // Create Locations json Array associated with key "results"
            JSONArray locationsJsonArray = rootJsonResponse.getJSONArray(RESULTS_TAG);

            int locationsJsonArraySize = locationsJsonArray.length();

            // Parse all items of array results to create Location items
            for (int i = 0; i < locationsJsonArraySize; i++) {

                JSONObject locationJsonObject = locationsJsonArray.getJSONObject(i);

                String id;
                String icon;
                String name;
                String address;
                Boolean isOpen = null;

                // If no data is received, display a default message DATA_NOT_AVAILABLE
                id = locationJsonObject.optString(PLACE_ID_TAG, DATA_NOT_AVAILABLE);
                icon = locationJsonObject.optString(ICON_TAG, DATA_NOT_AVAILABLE);
                name = locationJsonObject.optString(NAME_TAG, DATA_NOT_AVAILABLE);
                address = locationJsonObject.optString(ADDRESS_TAG, DATA_NOT_AVAILABLE);

                JSONObject openingHoursJsonObject = locationJsonObject.optJSONObject(OPENING_HOURS_TAG);

                if(openingHoursJsonObject != null) {
                    isOpen = openingHoursJsonObject.optBoolean(OPEN_NOW_TAG);
                }

                // Create Location item with values parsed and extracted from Json data
                Location locationItem = new Location(id, icon, name, address, isOpen);
                locations.add(locationItem);
            }

        } catch (JSONException e) {
            Log.e(HttpUtils.LOG_TAG, "Problem parsing the locations JSON results", e);
        }

        return locations;


    }

}
