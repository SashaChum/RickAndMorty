package com.chumikov.rickandmorty.presentation.fragments.view_models

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterEpisodesUseCase
import com.chumikov.rickandmorty.presentation.fragments.EpisodesFragmentArgs
import com.chumikov.rickandmorty.presentation.fragments.EpisodesLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = EpisodesFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _status =
        MutableStateFlow<EpisodesLoadingState>(EpisodesLoadingState.Loading)
    val status = _status.asStateFlow()

    init {
        load()
        Log.d("inspection", "EpisodesViewModel $this")
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val episodeList = getCharacterEpisodesUseCase(args.episodesId.toList())
                _status.value = EpisodesLoadingState.Success(episodeList)
            } catch (e: Exception) {
                _status.value = EpisodesLoadingState.Error
            }
        }
    }

    fun retry() {
        _status.value = EpisodesLoadingState.Loading
        load()
    }
}