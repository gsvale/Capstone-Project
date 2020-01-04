package com.example.capstoneproject.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.capstoneproject.BuildConfig;
import com.example.capstoneproject.R;
import com.example.capstoneproject.database.RoomDatabase;
import com.example.capstoneproject.databinding.ActivityLocationDetailBinding;
import com.example.capstoneproject.models.LocationViewModel;
import com.example.capstoneproject.objects.Location;
import com.example.capstoneproject.widget.GetLastLocationService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import static com.example.capstoneproject.objects.Location.LAST_SEEN_LOCATION_ID;

public class LocationDetailActivity extends AppCompatActivity {

    private ActivityLocationDetailBinding mBinding;

    // Location Item
    private Location mLocation;

    // Database instance
    private RoomDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set DataBinding instance
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_detail);

        // Initialize database variable
        mDatabase = RoomDatabase.getInstance(getApplicationContext());

        // If intent is null, finish activity and show toast message
        Intent intent = getIntent();
        if (intent == null) {
            closeActivity();
            return;
        }

        // If location is null, finish activity and show toast message
        mLocation = intent.getParcelableExtra(Location.LOCATION_TAG);
        if (mLocation == null) {
            closeActivity();
            return;
        }

        setTitle(mLocation.getName());

        //Call method to init ads
        initAds();

        //Call method update last seen location, in widget as well
        updateLastSeenLocation();

        //Call method get place details from Location object from intent
        getPlaceDetails();

    }

    // Method to init Ads
    private void initAds() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mBinding.adView.loadAd(adRequest);

    }

    // Method to update last seen location using Room, also updating Widget provider
    private void updateLastSeenLocation() {


        final LocationViewModel viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LocationViewModel.class);
        viewModel.getLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                // Insert or Update new last location seen
                if (locations == null || locations.isEmpty()) {
                    mDatabase.locationDao().insertLocation(new Location(mLocation.getName(), mLocation.getAddress(), mLocation.isOpen()));
                } else {
                    for (Location location : locations) {
                        if (location.getId().equals(LAST_SEEN_LOCATION_ID)) {
                            mDatabase.locationDao().updateLocation(new Location(mLocation.getName(), mLocation.getAddress(), mLocation.isOpen()));
                        }
                    }
                }

                // Remove observer after receiving last location info and updating it
                viewModel.getLocations().removeObserver(this);

                //Update Widget on UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update widget with new location
                        GetLastLocationService.startActionGetLastLocation(LocationDetailActivity.this);
                    }
                });
            }
        });


    }

    // Method to get Place details from Location object and display results in UI
    private void getPlaceDetails() {

        // Init progress bar loading
        mBinding.loadingPb.setVisibility(View.VISIBLE);
        mBinding.locationLl.setVisibility(View.GONE);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), BuildConfig.API_KEY);

        // Create a new Places client instance
        final PlacesClient placesClient = Places.createClient(this);

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.PHONE_NUMBER,
                Place.Field.PHOTO_METADATAS,
                Place.Field.TYPES,
                Place.Field.USER_RATINGS_TOTAL,
                Place.Field.WEBSITE_URI);

        // Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(mLocation.getId(), placeFields);

        // Request to fetch location details and show them in the UI, or show error toast
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {

                final Place place = response.getPlace();

                // Get the photo metadata.
                PhotoMetadata photoMetadata = null;

                if (place.getPhotoMetadatas() != null && !place.getPhotoMetadatas().isEmpty()) {
                    photoMetadata = place.getPhotoMetadatas().get(0);
                }

                if (photoMetadata != null) {

                    // Create a FetchPhotoRequest.
                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .setMaxWidth(150)
                            .setMaxHeight(150)
                            .build();

                    // Request to fetch photo bitmap to display it in the UI, or show error
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                        @Override
                        public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                            Bitmap bitmap = fetchPhotoResponse.getBitmap();
                            mBinding.locationImageIv.setImageBitmap(bitmap);
                            updateUI(place);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            updateUI(place);
                        }
                    });

                } else {
                    updateUI(place);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showErrorToast();
            }
        });

    }

    // Method called to update UI with details from Place item
    private void updateUI(final Place place) {
        mBinding.loadingPb.setVisibility(View.GONE);


        // Set Place Phone Number and click event to call it
        if (!TextUtils.isEmpty(place.getPhoneNumber())) {
            mBinding.locationPhoneTv.setText(place.getPhoneNumber());
            mBinding.locationPhoneTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + place.getPhoneNumber()));
                    startActivity(intent);
                }
            });
        } else {
            mBinding.locationPhoneTv.setText("-");
        }

        // Set Place Types
        if (place.getTypes() != null && !place.getTypes().isEmpty()) {
            int index = 0;
            for (Place.Type type : place.getTypes()) {
                if (index != 0) {
                    mBinding.locationTypesTv.append(", ");
                }
                mBinding.locationTypesTv.append(type.name());
                index++;
            }
        } else {
            mBinding.locationTypesTv.setText("-");
        }

        // Set Place User Ratings Total
        if (place.getUserRatingsTotal() != null && place.getUserRatingsTotal() > -1) {
            mBinding.locationUserRatingsTotalTv.setText(String.valueOf(place.getUserRatingsTotal()));
        } else {
            mBinding.locationUserRatingsTotalTv.setText("-");
        }

        // Set Place Website and click event to view it in the browser
        if (place.getWebsiteUri() != null && !place.getWebsiteUri().toString().isEmpty()) {
            mBinding.locationWebsiteTv.setText(place.getWebsiteUri().toString());
            mBinding.locationWebsiteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(place.getWebsiteUri());
                    startActivity(intent);
                }
            });
        } else {
            mBinding.locationWebsiteTv.setText("-");
        }

        mBinding.locationLl.setVisibility(View.VISIBLE);
    }

    // Method to show error toast message
    private void showErrorToast() {
        mBinding.loadingPb.setVisibility(View.GONE);
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Method to close detail activity and show error toast message
    private void closeActivity() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
