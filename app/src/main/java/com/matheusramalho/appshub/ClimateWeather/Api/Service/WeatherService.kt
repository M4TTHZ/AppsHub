package com.matheusramalho.appshub.ClimateWeather.Api.Service

import retrofit2.http.GET
import retrofit2.http.Query
import com.matheusramalho.appshub.ClimateWeather.Api.Model.WeatherResponse

interface WeatherService {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}