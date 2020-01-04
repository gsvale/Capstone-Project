package com.example.capstoneproject.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * The type Location.
 */
@Entity(tableName = "location")
public class Location implements Parcelable {

    /**
     * The constant LOCATION_TAG.
     */
    public static final String LOCATION_TAG = Location.class.getSimpleName();

    /**
     * The constant LAST_SEEN_LOCATION_ID.
     */
    public static final String LAST_SEEN_LOCATION_ID = "1";

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private String mId = LAST_SEEN_LOCATION_ID;


    @Ignore
    private String mIcon;

    private String mName;
    private String mAddress;
    private Boolean mIsOpen;

    /**
     * Instantiates a new Location.
     */
    @Ignore
    public Location() {
    }

    /**
     * Instantiates a new Location.
     *
     * @param name    the name
     * @param address the address
     * @param isOpen  the is open
     */
    public Location(String name, String address, Boolean isOpen) {
        this.mName = name;
        this.mAddress = address;
        this.mIsOpen = isOpen;
    }

    /**
     * Instantiates a new Location.
     *
     * @param id      the id
     * @param icon    the icon
     * @param name    the name
     * @param address the address
     * @param isOpen  the is open
     */
    @Ignore
    public Location(@NonNull String id, String icon, String name, String address, Boolean isOpen) {
        this.mId = id;
        this.mIcon = icon;
        this.mName = name;
        this.mAddress = address;
        this.mIsOpen = isOpen;
    }


    /**
     * Instantiates a new Location.
     *
     * @param in the in
     */
    protected Location(Parcel in) {
        mId = in.readString();
        mIcon = in.readString();
        mName = in.readString();
        mAddress = in.readString();
        byte tmpMIsOpen = in.readByte();
        mIsOpen = tmpMIsOpen == 0 ? null : tmpMIsOpen == 1;
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.mId = id;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return mAddress;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.mAddress = address;
    }

    /**
     * Is open boolean.
     *
     * @return the boolean
     */
    public Boolean isOpen() {
        return mIsOpen;
    }

    /**
     * Sets is open.
     *
     * @param isOpen the is open
     */
    public void setIsOpen(Boolean isOpen) {
        this.mIsOpen = isOpen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mIcon);
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeByte((byte) (mIsOpen == null ? 0 : mIsOpen ? 1 : 2));
    }
}
