package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val characterId: Int
) : ViewModel() {

    private val _status =
        MutableStateFlow<CharacterDetailsLoadingState>(CharacterDetailsLoadingState.Loading)
    val status = _status.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val characterDetails = getCharacterDetailsUseCase(characterId)
                _status.value = CharacterDetailsLoadingState.Success(characterDetails)
            } catch (e: Exception) {
                _status.value = CharacterDetailsLoadingState.Error
            }
        }
    }

    fun retry() {
        _status.value = CharacterDetailsLoadingState.Loading
        load()
    }

}