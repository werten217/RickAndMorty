package com.example.rickandmorty.repository

import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterResponse
import com.example.rickandmorty.network.CharacterApiService

class CharacterRepository(private val api: CharacterApiService) {
    suspend fun getCharacters(page: Int = 1): CharacterResponse = api.getCharacters(page)
    suspend fun getCharacterById(id: Int): Character = api.getCharacterById(id)
}