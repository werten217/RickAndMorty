package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(private val repo: CharacterRepository) : ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    init {
        refreshCharacters()
    }

    fun refreshCharacters(onComplete: (() -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getCharacters()
                _characters.emit(response.results)
            } finally {
                onComplete?.invoke()
            }
        }
    }

    fun getCharacterDetail(id: Int): StateFlow<Character?> {
        val flow = MutableStateFlow<Character?>(null)
        viewModelScope.launch(Dispatchers.IO) {
            val character = repo.getCharacterById(id)
            flow.emit(character)
        }
        return flow
    }
}