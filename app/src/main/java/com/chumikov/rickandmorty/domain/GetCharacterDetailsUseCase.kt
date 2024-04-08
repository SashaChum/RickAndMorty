package com.chumikov.rickandmorty.domain

import javax.inject.Inject


class GetCharacterDetailsUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(id: Int): CharacterDetails {
        return repository.getCharacterDetails(id)
    }
}