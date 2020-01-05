package com.example.capstoneproject.loaders;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.capstoneproject.BuildConfig;
import com.example.capstoneproject.R;
import com.example.capstoneproject.objects.Location;
import com.example.capstoneproject.utils.ApiUtils;
import com.example.capstoneproject.utils.HttpUtils;

import java.util.List;

public class LocationsLoader extends AsyncTaskLoader<List<Location>> {

    // Query that informs the Continent selected
    private String mQuery;

    // Variable to save location data results
    private List<Location> mJsonLocationData;

    public LocationsLoader(Context context, String query) {
        super(context);
        mQuery = query;
    }

    // First is executed OnStartLoading() that will call forceLoad() method which triggers the loadInBackground() method to execute
    @Override
    protected void onStartLoading() {

        // If there is cached data, deliver it, or else force load is called
        if(mJsonLocationData != null){
            deliverResult(mJsonLocationData);
        }
        else{
            forceLoad();
        }
    }

    // Return cached data from loader
    @Override
    public void deliverResult(List<Location> locations) {
        mJsonLocationData = locations;
        super.deliverResult(locations);
    }

    // Method called in background, to fetch data needed from API
    @Nullable
    @Override
    public List<Location> loadInBackground() {
        if (mQuery == null) {
            return null;
        }

        return fetchLocationsDataFromAPI();
    }

    // Method called to fetch Location data from API
    private List<Location> fetchLocationsDataFromAPI() {

        // URI builder
        Uri.Builder builder = new Uri.Builder();

        // Get Query from Continent string received, to search locations in that continent only
        String queryValue = ApiUtils.getQueryValue(getContext(), mQuery);

        // Build URL for API call
        builder.scheme(ApiUtils.SCHEME)
                .authority(ApiUtils.AUTHORITY)
                .appendEncodedPath(ApiUtils.PATH)
                .appendQueryParameter(ApiUtils.QUERY_TEXT, queryValue)
                .appendQueryParameter(ApiUtils.QUERY_KEY, getContext().getString(R.string.places_key));

        return HttpUtils.fetchLocationsData(builder.build().toString());
    }


}
