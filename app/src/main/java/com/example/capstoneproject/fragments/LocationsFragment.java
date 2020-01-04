package com.example.capstoneproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapters.LocationsAdapter;
import com.example.capstoneproject.databinding.FragmentLocationsBinding;
import com.example.capstoneproject.loaders.LocationsLoader;
import com.example.capstoneproject.objects.Location;
import com.example.capstoneproject.utils.NetworkUtils;

import java.util.List;

public class LocationsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Location>> {

    private static final String CONTINENT_TAG = "CONTINENT";

    // Loader ID
    private static final int LOCATIONS_LOADER_ID = 1;

    private FragmentLocationsBinding mBinding;

    // Continent value to get locations from there
    private String mContinent;

    private LocationsAdapter mAdapter;

    private LoaderManager mLoaderManager;

    public static LocationsFragment newInstance(String continent) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putString(CONTINENT_TAG, continent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Continent value from arguments received
        if (getArguments() != null) {
            mContinent = getArguments().getString(CONTINENT_TAG);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Set DataBinding instance
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_locations, container, false);

        // Set LinearLayoutManager for RecyclerView
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext());

        // Set LinearLayoutManager to recyclerView
        mBinding.locationsRv.setLayoutManager(layoutManager);

        // Create adapter with an empty list on start
        mAdapter = new LocationsAdapter(getContext());

        // Set adapter in RecyclerView
        mBinding.locationsRv.setAdapter(mAdapter);

        // Fetch locations data method call
        loadLocationsData();

        return mBinding.getRoot();
    }

    // Method to retrieve locations data, depending of network info
    private void loadLocationsData() {

        // Check if network/internet is available
        if (NetworkUtils.isConnected(requireContext())) {

            // If its available, init loader, if loader already exists , restart it
            mLoaderManager = LoaderManager.getInstance(this);

            if (mLoaderManager.getLoader(LOCATIONS_LOADER_ID) != null) {
                mLoaderManager.restartLoader(LOCATIONS_LOADER_ID, null, this);
            } else {
                mLoaderManager.initLoader(LOCATIONS_LOADER_ID, null, this);
            }

            mBinding.loadingPb.setVisibility(View.VISIBLE);
        } else {

            // If not available, display no internet connection warning
            mBinding.loadingPb.setVisibility(View.GONE);
            mBinding.locationsRv.setVisibility(View.GONE);
            mBinding.messageTv.setText(R.string.no_internet_connection);
            mBinding.messageTv.setVisibility(View.VISIBLE);
        }

    }

    // Method called to create the loader
    @NonNull
    @Override
    public Loader<List<Location>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LocationsLoader(requireContext(), mContinent);
    }

    // Method called when loading of information/background thread is finished, updating the UI
    @Override
    public void onLoadFinished(@NonNull Loader<List<Location>> loader, List<Location> data) {
        updateUI(data);
    }

    // Method to update UI according to data received
    private void updateUI(List<Location> data) {
        mBinding.loadingPb.setVisibility(View.GONE);

        // In case no locations are available to display
        mBinding.messageTv.setText(R.string.no_locations_available);

        // Clear adapter before updating it
        mAdapter.clearData();

        // Add data to adapter, if there is data, or else shows empty view
        if (data != null && !data.isEmpty()) {
            mAdapter.setLocationsData(data);
            mBinding.locationsRv.setVisibility(View.VISIBLE);
            mBinding.messageTv.setVisibility(View.GONE);
        } else {
            mBinding.locationsRv.setVisibility(View.GONE);
            mBinding.messageTv.setVisibility(View.VISIBLE);
        }

    }

    // Method that is called when loader is reset, and then adapter is cleared
    @Override
    public void onLoaderReset(@NonNull Loader<List<Location>> loader) {
        mAdapter.clearData();
    }
}
