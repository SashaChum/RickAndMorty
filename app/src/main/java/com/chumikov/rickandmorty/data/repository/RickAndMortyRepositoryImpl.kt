package com.chumikov.rickandmorty.data.repository

import com.chumikov.rickandmorty.domain.Character
import com.chumikov.rickandmorty.domain.CharacterDetails
import com.chumikov.rickandmorty.domain.Episode
import com.chumikov.rickandmorty.domain.RickAndMortyRepository

class RickAndMortyRepositoryImpl(): RickAndMortyRepository {
    override fun getCharacterList(): List<Character> {
        TODO("Not yet implemented")
    }

    override fun getCharacterDetails(id: Int): CharacterDetails {
        TODO("Not yet implemented")
    }

    override fun getCharacterEpisodes(list: List<Int>): List<Episode> {
        TODO("Not yet implemented")
    }
}