package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class CharacterViewModel(private val repo: CharacterRepository) : ViewModel() {


    val characters: StateFlow<List<Character>> =
        flow {
            val response = repo.getCharacters()
            emit(response.results)
        }
            .flowOn(Dispatchers.IO) // Запуск в фоне
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )

    // Детали персонажа как Flow
    fun getCharacterDetail(id: Int): StateFlow<Character?> =
        flow {
            val character = repo.getCharacterById(id)
            emit(character)
        }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = null
            )
}