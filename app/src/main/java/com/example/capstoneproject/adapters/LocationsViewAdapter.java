package com.example.capstoneproject.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.capstoneproject.R;
import com.example.capstoneproject.fragments.LocationsFragment;

public class LocationsViewAdapter extends FragmentPagerAdapter {

    private final static Integer PAGE_COUNT = 4;

    private Context mContext;
    private FragmentManager mManager;

    public LocationsViewAdapter(Context context, FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
        this.mManager = manager;
    }

    // Get item depending of current position
    @NonNull
    @Override
    public Fragment getItem(int position) {
        String continent = mContext.getString(R.string.continent_europe);

        if (position == 0) {
            continent = mContext.getString(R.string.continent_europe);
        } else if (position == 1) {
            continent = mContext.getString(R.string.continent_africa);
        } else if (position == 2) {
            continent = mContext.getString(R.string.continent_america);
        } else if (position == 3) {
            continent = mContext.getString(R.string.continent_asia);
        }

        return LocationsFragment.newInstance(continent);
    }

    // Get page title depending of current position
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.continent_europe);
        } else if (position == 1) {
            return mContext.getString(R.string.continent_africa);
        } else if (position == 2) {
            return mContext.getString(R.string.continent_america);
        } else if (position == 3) {
            return mContext.getString(R.string.continent_asia);
        }
        return mContext.getString(R.string.continent_europe);
    }

    // Get number of items/fragments of view pager
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
