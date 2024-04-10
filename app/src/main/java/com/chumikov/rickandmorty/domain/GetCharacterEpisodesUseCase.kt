package com.chumikov.rickandmorty.domain

import javax.inject.Inject


class GetCharacterEpisodesUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(list: List<Int>): List<Episode> {
        return repository.getCharacterEpisodes(list)
    }
}