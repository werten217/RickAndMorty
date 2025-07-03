package com.example.rickandmorty.repository

import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.EpisodeResponse
import com.example.rickandmorty.network.EpisodeApiService

class EpisodeRepository(private val api: EpisodeApiService) {
    suspend fun getEpisodes(page: Int = 1): EpisodeResponse = api.getEpisodes(page)
    suspend fun getEpisodeById(id: Int): Episode = api.getEpisodeById(id)
}