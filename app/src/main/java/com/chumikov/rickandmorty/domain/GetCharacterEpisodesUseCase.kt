package com.chumikov.rickandmorty.domain


class GetCharacterEpisodesUseCase(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(list: List<Int>): List<Episode> {
        return repository.getCharacterEpisodes(list)
    }
}