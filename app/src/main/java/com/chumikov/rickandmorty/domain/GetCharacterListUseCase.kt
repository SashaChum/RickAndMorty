package com.chumikov.rickandmorty.domain


class GetCharacterListUseCase (
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(): List<Character> {
        return repository.getCharacterList()
    }
}