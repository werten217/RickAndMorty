package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.FavoriteCharacter
import com.example.rickandmorty.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val favorites: StateFlow<List<FavoriteCharacter>> =
        repository.favorites.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            repository.addFavorite(character)
        }
    }

    fun removeFavorite(character: FavoriteCharacter) {
        viewModelScope.launch {
            repository.removeFavorite(character)
        }
    }

    suspend fun isFavorite(characterId: Int): Boolean {
        return repository.isFavorite(characterId)
    }
}