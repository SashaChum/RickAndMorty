package com.chumikov.rickandmorty.presentation

import com.chumikov.rickandmorty.domain.Episode

sealed interface EpisodesLoadingState {

    data object Loading: EpisodesLoadingState
    data object Error: EpisodesLoadingState
    data class Success(val data: List<Episode>): EpisodesLoadingState
}