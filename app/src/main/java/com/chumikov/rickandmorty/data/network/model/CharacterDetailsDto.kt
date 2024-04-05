package com.chumikov.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName

data class CharacterDetailsDto (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val imageUrl: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("location")
    val location: LocationDto,

    @SerializedName("episode")
    val episodeUrls: List<String>
)