package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

class GetCharacterDetailsUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(id: Int): LiveData<CharacterDetails> {
        return repository.getCharacterDetails(id)
    }
}