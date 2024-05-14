package com.chumikov.rickandmorty.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chumikov.rickandmorty.data.CharacterPagingDataSource
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val characterPagingDataSource: CharacterPagingDataSource
) : ViewModel() {

    val characters = Pager(PagingConfig(pageSize = 20)) { characterPagingDataSource }
        .flow
        .cachedIn(viewModelScope)

    init {
        Log.d("My Inspection", "CharacterListViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("My Inspection", "CharacterListViewModel onCleared")
    }
}
