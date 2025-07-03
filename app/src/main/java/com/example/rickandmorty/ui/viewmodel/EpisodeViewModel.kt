package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.repository.EpisodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodeViewModel(private val repo: EpisodeRepository): ViewModel() {
    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    private val _episodeDetail = MutableStateFlow<Episode?>(null)
    val episodeDetail: StateFlow<Episode?> = _episodeDetail

    fun fetchEpisodes() {
        viewModelScope.launch {
            val response = repo.getEpisodes()
            _episodes.value = response.results
        }
    }

    fun fetchEpisodeDetail(id: Int) {
        viewModelScope.launch {
            _episodeDetail.value = repo.getEpisodeById(id)
        }
    }
}