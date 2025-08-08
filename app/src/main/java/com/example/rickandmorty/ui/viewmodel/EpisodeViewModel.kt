package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeViewModel(private val repo: EpisodeRepository): ViewModel() {
    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    private val _episodeDetail = MutableStateFlow<Episode?>(null)
    val episodeDetail: StateFlow<Episode?> = _episodeDetail

    fun fetchEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getEpisodes()
            withContext(Dispatchers.Main) {
            _episodes.value = response.results
        }
    }
    }

    fun fetchEpisodeDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val character = repo.getEpisodeById(id)
            withContext(Dispatchers.Main) {
                _episodeDetail.value = character
            }
        }
    }
}