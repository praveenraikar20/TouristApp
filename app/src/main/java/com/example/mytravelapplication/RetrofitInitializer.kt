package com.example.mytravelapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInitializer {
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.tomtom.com/search/2/geocode/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getInstance(): Retrofit {
        return retrofit
    }
}