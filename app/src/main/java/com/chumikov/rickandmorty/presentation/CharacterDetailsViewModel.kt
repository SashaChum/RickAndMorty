package com.chumikov.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chumikov.rickandmorty.domain.CharacterDetails
import com.chumikov.rickandmorty.domain.GetCharacterDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val characterId: Int
) : ViewModel() {

    private val _characterDetails = MutableLiveData<CharacterDetails>()
    val characterDetails: LiveData<CharacterDetails>
        get() = _characterDetails

    init {
        viewModelScope.launch {
            val characterDetails = getCharacterDetailsUseCase(characterId)
            _characterDetails.value = characterDetails
        }
    }
}