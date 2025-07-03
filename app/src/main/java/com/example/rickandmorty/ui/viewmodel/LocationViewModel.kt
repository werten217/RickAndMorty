package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Location
import com.example.rickandmorty.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val repo: LocationRepository): ViewModel() {
    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>> = _locations

    private val _locationDetail = MutableStateFlow<Location?>(null)
    val locationDetail: StateFlow<Location?> = _locationDetail

    fun fetchLocations() {
        viewModelScope.launch {
            val response = repo.getLocations()
            _locations.value = response.results
        }
    }

    fun fetchLocationDetail(id: Int) {
        viewModelScope.launch {
            _locationDetail.value = repo.getLocationById(id)
        }
    }
}