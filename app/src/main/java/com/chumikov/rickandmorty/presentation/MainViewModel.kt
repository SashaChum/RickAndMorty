package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.chumikov.rickandmorty.domain.GetCharacterListUseCase
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val repository = RickAndMortyRepositoryImpl()
    private val getCharacterListUseCase = GetCharacterListUseCase(repository)

    fun getCharacterList() {
        viewModelScope.launch {
            val characterList = getCharacterListUseCase()
        }
    }
}