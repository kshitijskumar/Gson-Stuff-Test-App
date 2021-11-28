package com.example.gsonstuffapp

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
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

    @GET("getOnlyName")
    fun getFewWithInstanceCreator() : Call<InstanceCreatorResponse>

    @GET("getOnlyName")
    fun getFewWithBackingFields() : Call<BackingFieldResponse>

    @GET("getAllValues")
    suspend fun getAllWithAllDefaultsForCache() : Response<AllDefaultsResponse>

    companion object {
        private val gson = GsonBuilder()
            .registerTypeAdapter(
                InstanceCreatorResponse::class.java,
                InstanceCreatorResponseAdapter()
            ).create()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://gson-stuff-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        fun getApiService() = retrofit.create(ApiService::class.java)
    }

    /**
     * Base url: https://gson-stuff-api.herokuapp.com/
     *
     * Responses:
     *
     * 1. getAllValues/ -> { "someName" : "someone", "someNumber" : 22 }
     *
     * 2. getOnlyName/ -> { "someName" : "someone" }
     *
     */
}