package com.chumikov.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class CharacterListDto(

    @SerializedName("info")
    val pageInfo: CharacterPageInfo,

    @SerializedName("results")
    val allCharacters: List<CharacterDto>
)
