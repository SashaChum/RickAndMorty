package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterEpisodesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val episodes: List<Int>
) : ViewModel() {

    private val _status = MutableLiveData<LoadingUiEpisodesState>()
    val status: LiveData<LoadingUiEpisodesState>
        get() = _status


    init {
        load()
    }

    private fun load() {

        viewModelScope.launch {
            _status.value = LoadingUiEpisodesState.Loading
            try {
                val episodeList = getCharacterEpisodesUseCase(episodes)
                _status.value = LoadingUiEpisodesState.Success(episodeList)
            } catch (e: Exception) {
                _status.value = LoadingUiEpisodesState.Error
            }
        }
    }

    fun retry() {
        load()
    }
}