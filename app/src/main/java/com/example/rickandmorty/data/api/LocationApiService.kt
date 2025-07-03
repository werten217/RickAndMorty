package com.example.rickandmorty.network

import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {
    @GET("location")
    suspend fun getLocations(@Query("page") page: Int = 1): LocationResponse

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): Location
}