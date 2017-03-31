
package com.example.ian_sibner.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Person {

    @SerializedName("organization")
    @Expose
    public String organization;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("rank")
    @Expose
    public Integer rank;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;

    // empty constructor for Parcelable
    public Person() {}

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
