package com.chumikov.rickandmorty.domain


class GetCharacterDetailsUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(id: Int): CharacterDetails {
        return repository.getCharacterDetails(id)
    }
}