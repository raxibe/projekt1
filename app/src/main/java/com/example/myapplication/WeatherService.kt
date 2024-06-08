package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query ("aqi") agi: String
    ): Call<WeatherResponse>
}