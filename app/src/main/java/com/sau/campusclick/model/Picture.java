package com.sau.campusclick.model;

/**
 * Created by saurabh on 2017-04-13.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("datecreated")
    @Expose
    private String datecreated;
    @SerializedName("username")
    @Expose
    private String username;

    public Picture(String id, String userid, String lat, String lon, String datecreated, String username) {
        super();
        this.id = id;
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
        this.datecreated = datecreated;
        this.username = username;
    }

    public Picture(Parcel parcel) {
        this(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(getUserid());
        parcel.writeString(getLat());
        parcel.writeString(getLon());
        parcel.writeString(getDatecreated());
        parcel.writeString(getUsername());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel parcel) {
            return new Picture(parcel);
        }

        @Override
        public Picture[] newArray(int i) {
            return new Picture[i];
        }
    };
}