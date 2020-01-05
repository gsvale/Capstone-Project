package com.example.capstoneproject.widget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.capstoneproject.database.RoomDatabase;
import com.example.capstoneproject.objects.Location;


import static com.example.capstoneproject.objects.Location.LAST_SEEN_LOCATION_ID;

public class GetLastLocationService extends JobIntentService{

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
