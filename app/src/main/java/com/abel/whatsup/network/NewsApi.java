package com.abel.whatsup.network;

import com.abel.whatsup.models.CountryNewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<CountryNewsResponse> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
