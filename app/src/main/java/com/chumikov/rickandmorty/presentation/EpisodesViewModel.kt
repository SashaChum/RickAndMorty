package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterEpisodesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val episodes: List<Int>
) : ViewModel() {

    private val _status = MutableLiveData<EpisodesLoadingState>()
    val status: LiveData<EpisodesLoadingState>
        get() = _status


    init {
        load()
    }

    private fun load() {

        viewModelScope.launch {
            _status.value = EpisodesLoadingState.Loading
            try {
                val episodeList = getCharacterEpisodesUseCase(episodes)
                _status.value = EpisodesLoadingState.Success(episodeList)
            } catch (e: Exception) {
                _status.value = EpisodesLoadingState.Error
            }
        }
    }

    fun retry() {
        load()
    }
}