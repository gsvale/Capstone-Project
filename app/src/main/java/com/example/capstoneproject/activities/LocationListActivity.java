package com.example.capstoneproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapters.LocationsViewAdapter;
import com.example.capstoneproject.databinding.ActivityLocationListBinding;

public class LocationListActivity extends AppCompatActivity {

    private ActivityLocationListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set DataBinding instance
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_list);

        // ViewPagerAdapter variable
        LocationsViewAdapter adapter = new LocationsViewAdapter(this, getSupportFragmentManager());

        // Set adapter in ViewPager variable
        mBinding.mainVp.setAdapter(adapter);

        // Get TabLayout variable and setup with ViewPager variable
        mBinding.mainTl.setupWithViewPager(mBinding.mainVp);

    }
}
