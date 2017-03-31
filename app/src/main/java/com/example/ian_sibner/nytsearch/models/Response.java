
package com.example.ian_sibner.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Response {

    @SerializedName("docs")
    @Expose
    public List<Doc> docs = null;

    // empty constructor for Parcelable
    public Response() {}

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

}
