package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val characterId: Int
) : ViewModel() {

    private val _status = MutableLiveData<LoadingUiState>()
    val status: LiveData<LoadingUiState>
        get() = _status

    init {
        load()
    }

    private fun load() {

        viewModelScope.launch {
            _status.value = LoadingUiState.Loading
            try {
                val characterDetails = getCharacterDetailsUseCase(characterId)
                _status.value = LoadingUiState.Success(characterDetails)
            } catch (e: Exception) {
                _status.value = LoadingUiState.Error
            }
        }
    }

    fun retry() {
        load()
    }

}