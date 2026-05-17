package com.matheusramalho.appshub.ClimateWeather.Api.Service

import com.matheusramalho.appshub.ClimateWeather.Api.Model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchLocations(
        @Query("name") cityName: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "pt"
    ) : GeocodingResponse
}