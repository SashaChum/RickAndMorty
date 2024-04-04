package com.chumikov.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName

data class CharacterListDto(

    @SerializedName("results")
    val allCharacters: List<CharacterDto>
)
