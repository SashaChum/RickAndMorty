package com.chumikov.rickandmorty.domain

import com.google.gson.annotations.SerializedName

data class AllCharacters(

    @SerializedName("results")
    val allCharacters: List<Character>
)
