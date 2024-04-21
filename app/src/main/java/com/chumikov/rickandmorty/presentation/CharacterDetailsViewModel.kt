package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel @AssistedInject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    @Assisted private val characterId: Int
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

    @AssistedFactory
    interface Factory {
        fun get(id: Int): CharacterDetailsViewModel
    }
}