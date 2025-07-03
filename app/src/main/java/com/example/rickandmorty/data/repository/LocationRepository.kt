package com.example.rickandmorty.repository

import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.LocationResponse
import com.example.rickandmorty.network.LocationApiService

class LocationRepository(private val api: LocationApiService) {
    suspend fun getLocations(page: Int = 1): LocationResponse = api.getLocations(page)
    suspend fun getLocationById(id: Int): Location = api.getLocationById(id)
}