package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.api.FavoriteCharacterDao
import com.example.rickandmorty.data.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteCharacterDao) {

    val favorites: Flow<List<FavoriteCharacter>> = dao.getAllFavorites()

    suspend fun addFavorite(character: FavoriteCharacter) {
        dao.insertFavorite(character)
    }

    suspend fun removeFavorite(character: FavoriteCharacter) {
        dao.deleteFavorite(character)
    }

    suspend fun isFavorite(characterId: Int): Boolean {
        return dao.isFavorite(characterId)
    }
}