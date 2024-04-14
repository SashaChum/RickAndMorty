package com.chumikov.rickandmorty.domain


interface RickAndMortyRepository {

    suspend fun getCharacterDetails(id: Int): CharacterDetails

    suspend fun getCharacterEpisodes(list: List<Int>): List<Episode>
}