package com.chumikov.rickandmorty.presentation

import com.chumikov.rickandmorty.domain.CharacterDetails

sealed interface LoadingUiDetailsState {

    object Loading: LoadingUiDetailsState
    object Error: LoadingUiDetailsState
    data class Success(val data: CharacterDetails): LoadingUiDetailsState
}