package com.chumikov.rickandmorty.domain

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val imageURL: String
)
