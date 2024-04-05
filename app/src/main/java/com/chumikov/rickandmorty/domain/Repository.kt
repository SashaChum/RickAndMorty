package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getCharacterList(): LiveData<List<Character>>

    fun getCharacterDetails(): LiveData<CharacterDetails>

    fun getCharacterEpisodes(): LiveData<List<Episode>>
}