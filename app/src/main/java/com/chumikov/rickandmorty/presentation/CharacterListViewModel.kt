package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.chumikov.rickandmorty.domain.Character
import com.chumikov.rickandmorty.domain.GetCharacterListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {


    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    init {
        viewModelScope.launch {
            val characterList = getCharacterListUseCase()
            _characters.value = characterList
        }
    }
}