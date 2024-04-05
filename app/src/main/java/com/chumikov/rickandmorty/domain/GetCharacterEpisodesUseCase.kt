package com.chumikov.rickandmorty.domain


class GetCharacterEpisodesUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(list: List<Int>): List<Episode> {
        return repository.getCharacterEpisodes(list)
    }
}