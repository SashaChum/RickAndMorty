package com.chumikov.rickandmorty.presentation.fragments.view_models

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.GetCharacterDetailsUseCase
import com.chumikov.rickandmorty.presentation.fragments.CharacterDetailsFragmentArgs
import com.chumikov.rickandmorty.presentation.fragments.CharacterDetailsLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = CharacterDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _status =
        MutableStateFlow<CharacterDetailsLoadingState>(CharacterDetailsLoadingState.Loading)
    val status = _status.asStateFlow()

    init {
        load()
        Log.d("inspection", "CharacterDetailsViewModel $this")
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val characterDetails = getCharacterDetailsUseCase(args.characterId)
                _status.value = CharacterDetailsLoadingState.Success(characterDetails)
            } catch (e: Exception) {
                _status.value = CharacterDetailsLoadingState.Error
            }
        }
    }

    fun retry() {
        _status.value = CharacterDetailsLoadingState.Loading
        load()
    }
}