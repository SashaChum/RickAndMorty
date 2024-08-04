package com.chumikov.rickandmorty.presentation.fragments.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chumikov.rickandmorty.data.CharacterPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterPagingDataSource: CharacterPagingDataSource
) : ViewModel() {

    init {
        Log.d("inspection", "CharacterListViewModel $this")
    }

    val characters = Pager(PagingConfig(pageSize = 20)) { characterPagingDataSource }
        .flow
        .cachedIn(viewModelScope)
}
