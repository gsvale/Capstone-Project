package com.example.capstoneproject.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstoneproject.objects.Location;

import java.util.List;

// Interface of Methods used on Room
@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    LiveData<List<Location>> loadLocations();

    @Query("SELECT * FROM location WHERE id = :id")
    Location loadLastLocationForWidget(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(Location location);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLocation(Location location);

}
