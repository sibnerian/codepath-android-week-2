
package com.example.ian_sibner.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedium {

    private static final String fullUrlPrefix = "http://www.nytimes.com/";

    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("rank")
    @Expose
    public Integer rank;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("subtype")
    @Expose
    public String subtype;
    @SerializedName("type")
    @Expose
    public String type;

    // empty constructor for Parcelable
    public Multimedium() {}

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public String getFullUrl() { return fullUrlPrefix + getUrl(); }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
