package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.Episode
import com.chumikov.rickandmorty.domain.GetCharacterEpisodesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val episodes: List<Int>
) : ViewModel() {

    private val _episodeList = MutableLiveData<List<Episode>>()
    val episodeList: LiveData<List<Episode>>
        get() = _episodeList

    init {
        viewModelScope.launch {
            val episodeList = getCharacterEpisodesUseCase(episodes)
            _episodeList.value = episodeList
        }
    }
}