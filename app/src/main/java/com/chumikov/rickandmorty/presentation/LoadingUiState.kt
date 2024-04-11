package com.chumikov.rickandmorty.presentation

import com.chumikov.rickandmorty.domain.CharacterDetails

sealed interface LoadingUiState {

    object Loading: LoadingUiState
    object Error: LoadingUiState
    data class Success(val data: CharacterDetails): LoadingUiState
}