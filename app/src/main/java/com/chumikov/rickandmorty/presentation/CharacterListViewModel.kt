package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.chumikov.rickandmorty.data.CharacterPagingDataSource
import com.chumikov.rickandmorty.domain.Character
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val characterPagingDataSource: CharacterPagingDataSource
) : ViewModel() {

    val characters: LiveData<PagingData<Character>> = Pager(PagingConfig(pageSize = 20)) {
        characterPagingDataSource
    }.liveData.cachedIn(viewModelScope)

}
