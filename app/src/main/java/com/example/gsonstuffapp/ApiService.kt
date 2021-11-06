package com.example.gsonstuffapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("getAllValues")
    fun getAllWithAllDefaults() : Call<AllDefaultsResponse>

    @GET("getAllValues")
    fun getAllWithFewDefaults() : Call<OnlyNumberDefaultResponse>

    @GET("getOnlyName")
    fun getFewWithAllDefaults() : Call<AllDefaultsResponse>

    @GET("getOnlyName")
    fun getFewWithFewDefaults() : Call<OnlyNumberDefaultResponse>

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://gson-stuff-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getApiService() = retrofit.create(ApiService::class.java)
    }
}