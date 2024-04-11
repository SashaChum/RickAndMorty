package com.chumikov.rickandmorty.presentation

sealed interface LoadingUiState {

    object Loading: LoadingUiState
    object Error: LoadingUiState
    object Success: LoadingUiState
}