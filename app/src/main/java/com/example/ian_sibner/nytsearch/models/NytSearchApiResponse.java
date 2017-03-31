
package com.example.ian_sibner.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class NytSearchApiResponse {

    @SerializedName("response")
    @Expose
    public Response response;

    // empty constructor for Parcelable
    public NytSearchApiResponse() {}


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
