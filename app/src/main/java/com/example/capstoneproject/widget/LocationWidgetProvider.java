package com.example.capstoneproject.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.capstoneproject.R;
import com.example.capstoneproject.objects.Location;

/**
 * Implementation of App Widget functionality.
 */
public class LocationWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Location location,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.location_widget);

        // Set values in widget views, if location object is not null
        if (location != null) {

            if (!TextUtils.isEmpty(location.getName())) {
                views.setTextViewText(R.id.location_name_tv, location.getName());
            }

            if (!TextUtils.isEmpty(location.getAddress())) {
                views.setTextViewText(R.id.location_address_tv, location.getAddress());
            }

            if (location.isOpen() != null) {
                if (location.isOpen()) {
                    views.setTextViewText(R.id.location_opening_tv, context.getString(R.string.open));
                    views.setTextColor(R.id.location_opening_tv, context.getResources().getColor(R.color.colorGreen));
                } else {
                    views.setTextViewText(R.id.location_opening_tv, context.getString(R.string.closed));
                    views.setTextColor(R.id.location_opening_tv, context.getResources().getColor(R.color.colorRed));
                }
            }

        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Start the intent service update widget action, the service takes care of updating the widgets UI
        GetLastLocationService.startActionGetLastLocation(context);
    }

    public static void updateLastLocationWidget(Context context, AppWidgetManager appWidgetManager, Location location,
                                                int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, location, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}