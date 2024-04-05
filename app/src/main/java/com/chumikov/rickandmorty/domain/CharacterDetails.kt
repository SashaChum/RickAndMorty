package com.chumikov.rickandmorty.domain

data class CharacterDetails (
    val id: Int,
    val name: String,
    val imageURL: String,
    val species: String,
    val status: String,
    val location: String,
    val episodes: List<String>
) {
    fun some() {
        TODO("Нужно ли в этом классе поле episodes?")
    }
}

