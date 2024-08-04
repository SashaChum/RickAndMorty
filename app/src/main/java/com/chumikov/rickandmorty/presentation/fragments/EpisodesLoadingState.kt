package com.chumikov.rickandmorty.presentation.fragments

import androidx.compose.runtime.Immutable
import com.chumikov.rickandmorty.domain.Episode

@Immutable
sealed interface EpisodesLoadingState {

    data object Loading: EpisodesLoadingState
    data object Error: EpisodesLoadingState
    data class Success(val data: List<Episode>): EpisodesLoadingState
}