package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

interface RickAndMortyRepository {

    fun getCharacterList(): LiveData<List<Character>>

    fun getCharacterDetails(id: Int): LiveData<CharacterDetails>

    fun getCharacterEpisodes(list: List<Int>): LiveData<List<Episode>>
}