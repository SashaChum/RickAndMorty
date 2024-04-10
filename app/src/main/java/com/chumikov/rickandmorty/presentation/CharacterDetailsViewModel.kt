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

    private val _textGood = MutableLiveData<Unit>()
    val textGood:LiveData<Unit>
        get() = _textGood

    private val _photoGood = MutableLiveData<Unit>()
    val photoGood:LiveData<Unit>
        get() = _photoGood

    init {
        viewModelScope.launch {
            val characterDetails = getCharacterDetailsUseCase(characterId)
            _characterDetails.value = characterDetails
            _textGood.value = Unit
        }
    }
}