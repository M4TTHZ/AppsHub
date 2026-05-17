package com.matheusramalho.appshub.ClimateWeather.Api.Model

data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)

// Resposta da API
data class GeocodingResponse(
    val results: List<LocationResult>? = null
)

data class LocationResult(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current_weather: CurrentWeather
)

data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Int,
    val weathercode: Int,
    val time: String
)