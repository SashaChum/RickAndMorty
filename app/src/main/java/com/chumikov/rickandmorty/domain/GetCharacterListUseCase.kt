package com.chumikov.rickandmorty.domain


class GetCharacterListUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(): List<Character> {
        return repository.getCharacterList()
    }
}