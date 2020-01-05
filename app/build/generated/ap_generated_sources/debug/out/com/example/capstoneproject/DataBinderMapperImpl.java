package com.example.capstoneproject;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.capstoneproject.databinding.ActivityLocationDetailBindingImpl;
import com.example.capstoneproject.databinding.ActivityLocationListBindingImpl;
import com.example.capstoneproject.databinding.FragmentLocationsBindingImpl;
import com.example.capstoneproject.databinding.LocationItemLayoutBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYLOCATIONDETAIL = 1;

  private static final int LAYOUT_ACTIVITYLOCATIONLIST = 2;

  private static final int LAYOUT_FRAGMENTLOCATIONS = 3;

  private static final int LAYOUT_LOCATIONITEMLAYOUT = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.capstoneproject.R.layout.activity_location_detail, LAYOUT_ACTIVITYLOCATIONDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.capstoneproject.R.layout.activity_location_list, LAYOUT_ACTIVITYLOCATIONLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.capstoneproject.R.layout.fragment_locations, LAYOUT_FRAGMENTLOCATIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.capstoneproject.R.layout.location_item_layout, LAYOUT_LOCATIONITEMLAYOUT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYLOCATIONDETAIL: {
          if ("layout/activity_location_detail_0".equals(tag)) {
            return new ActivityLocationDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_location_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOCATIONLIST: {
          if ("layout/activity_location_list_0".equals(tag)) {
            return new ActivityLocationListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_location_list is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLOCATIONS: {
          if ("layout/fragment_locations_0".equals(tag)) {
            return new FragmentLocationsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_locations is invalid. Received: " + tag);
        }
        case  LAYOUT_LOCATIONITEMLAYOUT: {
          if ("layout/location_item_layout_0".equals(tag)) {
            return new LocationItemLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for location_item_layout is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_location_detail_0", com.example.capstoneproject.R.layout.activity_location_detail);
      sKeys.put("layout/activity_location_list_0", com.example.capstoneproject.R.layout.activity_location_list);
      sKeys.put("layout/fragment_locations_0", com.example.capstoneproject.R.layout.fragment_locations);
      sKeys.put("layout/location_item_layout_0", com.example.capstoneproject.R.layout.location_item_layout);
    }
  }
}
