package com.chumikov.rickandmorty.presentation

import androidx.compose.runtime.Immutable
import com.chumikov.rickandmorty.domain.CharacterDetails

@Immutable
sealed interface CharacterDetailsLoadingState {

    data object Loading: CharacterDetailsLoadingState
    data object Error: CharacterDetailsLoadingState
    data class Success(val data: CharacterDetails): CharacterDetailsLoadingState
}