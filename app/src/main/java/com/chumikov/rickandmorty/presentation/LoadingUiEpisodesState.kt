package com.chumikov.rickandmorty.presentation

import com.chumikov.rickandmorty.domain.Episode

sealed interface LoadingUiEpisodesState {

    object Loading: LoadingUiEpisodesState
    object Error: LoadingUiEpisodesState
    data class Success(val data: List<Episode>): LoadingUiEpisodesState
}