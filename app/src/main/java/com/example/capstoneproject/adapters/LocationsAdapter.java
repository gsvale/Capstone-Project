package com.example.capstoneproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activities.LocationDetailActivity;
import com.example.capstoneproject.databinding.LocationItemLayoutBinding;
import com.example.capstoneproject.objects.Location;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationsAdapterViewHolder> {

    private LayoutInflater mLayoutInflater;

    private Context mContext;

    private List<Location> locations;

    // Constructor receive a clickHandler to be called in MoviesActivity
    public LocationsAdapter(Context context) {
        mContext = context;
        locations = new ArrayList<>();
    }

    @NonNull
    @Override
    public LocationsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        LocationItemLayoutBinding binding = DataBindingUtil.inflate(
                mLayoutInflater,
                R.layout.location_item_layout,
                parent,
                false
        );

        return new LocationsAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsAdapterViewHolder holder, int position) {
        Location item = locations.get(position);

        // Show icon from location with aid of Picasso library
        Picasso.get()
                .load(item.getIcon())
                .placeholder(R.drawable.ic_place_icon)
                .error(R.drawable.ic_place_icon)
                .into(holder.mBinding.locationPlaceIv);

        // Set location name
        holder.mBinding.locationNameTv.setText(item.getName());

        // Set location address
        holder.mBinding.locationAddressTv.setText(item.getAddress());

        // Set location current status/opening info
        if (item.isOpen() != null) {
            if (item.isOpen()) {
                holder.mBinding.locationOpeningTv.setText(mContext.getString(R.string.open));
                holder.mBinding.locationOpeningTv.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            } else {
                holder.mBinding.locationOpeningTv.setText(mContext.getString(R.string.closed));
                holder.mBinding.locationOpeningTv.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            }
            holder.mBinding.locationOpeningTv.setVisibility(View.VISIBLE);
        } else {
            holder.mBinding.locationOpeningTv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (null == locations) return 0;
        return locations.size();
    }

    // Clear data, and set new data then notify that the data has changed
    public void setLocationsData(List<Location> locationsData) {
        locations.clear();
        locations.addAll(locationsData);
        notifyDataSetChanged();
    }

    // Clear data then notify
    public void clearData() {
        locations.clear();
        notifyDataSetChanged();
    }

    public class LocationsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LocationItemLayoutBinding mBinding;

        LocationsAdapterViewHolder(LocationItemLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        // When an item is click, go to the Location Detail Activity with Location from item clicked
        @Override
        public void onClick(View v) {
            Intent detailsIntent = new Intent(mContext, LocationDetailActivity.class);
            detailsIntent.putExtra(Location.LOCATION_TAG, locations.get(getAdapterPosition()));
            mContext.startActivity(detailsIntent);
        }
    }


}
