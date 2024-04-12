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

    private val _status = MutableLiveData<LoadingUiDetailsState>()
    val status: LiveData<LoadingUiDetailsState>
        get() = _status

    init {
        load()
    }

    private fun load() {

        viewModelScope.launch {
            _status.value = LoadingUiDetailsState.Loading
            try {
                val characterDetails = getCharacterDetailsUseCase(characterId)
                _status.value = LoadingUiDetailsState.Success(characterDetails)
            } catch (e: Exception) {
                _status.value = LoadingUiDetailsState.Error
            }
        }
    }

    fun retry() {
        load()
    }

}