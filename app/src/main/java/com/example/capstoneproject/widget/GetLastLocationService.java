package com.example.capstoneproject.widget;

import android.app.IntentService;
import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;
import androidx.databinding.Observable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstoneproject.database.RoomDatabase;
import com.example.capstoneproject.models.LocationViewModel;
import com.example.capstoneproject.objects.Location;

import java.util.List;

import static com.example.capstoneproject.objects.Location.LAST_SEEN_LOCATION_ID;

public class GetLastLocationService extends JobIntentService{

    public static final String GET_LAST_LOCATION_SERVICE_TAG = GetLastLocationService.class.getSimpleName();

    public static final int JOB_ID = 1;

    public static final String ACTION_GET_LAST_LOCATION = "com.example.capstoneproject.action.get_last_location";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Method to start Action intent
    public static void startActionGetLastLocation(Context context) {
        Intent intent = new Intent(context, GetLastLocationService.class);
        intent.setAction(ACTION_GET_LAST_LOCATION);
        enqueueWork(context, GetLastLocationService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_LAST_LOCATION.equals(action)) {
                handleActionGetLastLocation();
            }
        }
    }

    // Method called to handle the respective action
    private void handleActionGetLastLocation() {

        //Get Last Location seen from Room database
        Location location = null;

        // Initialize database variable
        RoomDatabase mDatabase = RoomDatabase.getInstance(getApplicationContext());

        // Get location from Room Dao
        location = mDatabase.locationDao().loadLastLocationForWidget(LAST_SEEN_LOCATION_ID);

        // Update widget with new location
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, LocationWidgetProvider.class));
        LocationWidgetProvider.updateLastLocationWidget(this, appWidgetManager, location, appWidgetIds);

    }


}
