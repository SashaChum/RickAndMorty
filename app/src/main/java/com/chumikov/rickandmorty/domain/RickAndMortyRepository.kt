package com.chumikov.rickandmorty.domain


interface RickAndMortyRepository {

    fun getCharacterList(): List<Character>

    fun getCharacterDetails(id: Int): CharacterDetails

    fun getCharacterEpisodes(list: List<Int>): List<Episode>
}