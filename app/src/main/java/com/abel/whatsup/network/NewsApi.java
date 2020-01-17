package com.abel.whatsup.network;

import com.abel.whatsup.models.CountryNewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("everything")
    Call<CountryNewsResponse> getEveryNews(
            @Query("q") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<CountryNewsResponse> getNewsBySource(
            @Query("sources") String Country,
            @Query("apiKey") String apiKey
    );
}
