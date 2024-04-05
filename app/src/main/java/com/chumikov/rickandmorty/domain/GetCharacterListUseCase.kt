package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

class GetCharacterListUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(): LiveData<List<Character>> {
        return repository.getCharacterList()
    }
}