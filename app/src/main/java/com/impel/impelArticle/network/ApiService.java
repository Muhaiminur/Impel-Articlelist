package com.impel.impelArticle.network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //get home article list
    @GET("top-headlines/")
    Call<JsonElement> get_home_articlelist(@Query("country") String country, @Query("category") String category, @Query("apiKey") String api);
}
