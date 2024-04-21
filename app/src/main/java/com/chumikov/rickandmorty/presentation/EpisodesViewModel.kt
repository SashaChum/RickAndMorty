package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterEpisodesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val episodes: List<Int>
) : ViewModel() {

    private val _status =
        MutableStateFlow<EpisodesLoadingState>(EpisodesLoadingState.Loading)
    val status = _status.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val episodeList = getCharacterEpisodesUseCase(episodes)
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