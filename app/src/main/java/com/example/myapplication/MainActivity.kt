package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var retrofitService:  WeatherService

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRetrofitService()
        binding.getWeatherData.setOnClickListener{
            getCurrentWether("Noyabrsk")
        }

    }
    private fun initRetrofitService(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(WeatherService::class.java)
    }

    private fun getCurrentWether(cityName: String){
        val call = retrofitService.getCurrentWeather(API_KEY, cityName, "no")

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful){
                    val weather = response.body()?.current
                    Log.d("CurrentWeather", "response: ${weather?.temp_c}")
                } else{
                    Log.d("CurrentWeather", "error: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable){
                Log.d("CurrentWeather", "trowable: $t")
            }
        })
    }
    companion object{
        const val API_KEY = "8917986851b9414e8df203528241301"//
        const val BASE_URL = "https://api.wetherapi.com/v1/"
    }
}