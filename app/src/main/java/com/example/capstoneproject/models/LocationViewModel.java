package com.example.capstoneproject.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstoneproject.database.RoomDatabase;
import com.example.capstoneproject.objects.Location;

import java.util.List;


// Location View Model to observe and update Last Location from Room
public class LocationViewModel extends AndroidViewModel {

    private LiveData<List<Location>> locations;

    public LocationViewModel(Application application) {
        super(application);
        RoomDatabase database = RoomDatabase.getInstance(this.getApplication());
        locations = database.locationDao().loadLocations();
    }

    public LiveData<List<Location>> getLocations() {
        return locations;
    }


}
