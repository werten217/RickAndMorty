package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(private val repo: CharacterRepository): ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _characterDetail = MutableStateFlow<Character?>(null)
    val characterDetail: StateFlow<Character?> = _characterDetail

    fun fetchCharacters() {
        viewModelScope.launch {
            val response = repo.getCharacters()
            _characters.value = response.results
        }
    }

    fun fetchCharacterDetail(id: Int) {
        viewModelScope.launch {
            _characterDetail.value = repo.getCharacterById(id)
        }
    }
}