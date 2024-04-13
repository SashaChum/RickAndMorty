package com.chumikov.rickandmorty.presentation

import com.chumikov.rickandmorty.domain.CharacterDetails

sealed interface CharacterDetailsLoadingState {

    data object Loading: CharacterDetailsLoadingState
    data object Error: CharacterDetailsLoadingState
    data class Success(val data: CharacterDetails): CharacterDetailsLoadingState
}