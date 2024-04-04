package com.chumikov.rickandmorty.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getListOfCharacter(): LiveData<List<Character>>

    fun getCharacterProfile(): LiveData<Character>
}