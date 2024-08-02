package com.chumikov.rickandmorty.data.mappers

import com.chumikov.rickandmorty.data.network.dto.CharacterDto
import com.chumikov.rickandmorty.domain.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor(){

    fun toDomain(characterDto: CharacterDto): Character {
        return Character(
            id = characterDto.id,
            name = characterDto.name,
            imageUrl = characterDto.imageUrl
        )
    }
}