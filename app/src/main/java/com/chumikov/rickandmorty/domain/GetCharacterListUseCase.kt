package com.chumikov.rickandmorty.domain

import javax.inject.Inject


class GetCharacterListUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(): List<Character> {
        return repository.getCharacterList()
    }
}