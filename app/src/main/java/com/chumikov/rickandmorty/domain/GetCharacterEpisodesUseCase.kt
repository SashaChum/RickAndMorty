package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

class GetCharacterEpisodesUseCase(
    private val repository: RickAndMortyRepository
) {
    operator fun invoke(list: List<Int>): LiveData<List<Episode>> {
        return repository.getCharacterEpisodes(list)
    }
}