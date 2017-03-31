package com.example.ian_sibner.nytsearch.api;

import com.example.ian_sibner.nytsearch.models.NytSearchApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ian_sibner on 3/28/17.
 */

interface NytSearchApiService {
    @GET("articlesearch.json")
    public Call<NytSearchApiResponse> getSearchResults(@QueryMap Map<String, String> options);
}