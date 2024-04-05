package com.chumikov.rickandmorty.domain

data class CharacterDetails (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val species: String,
    val status: String,
    val location: String,
    val episodes: List<Int>
)

