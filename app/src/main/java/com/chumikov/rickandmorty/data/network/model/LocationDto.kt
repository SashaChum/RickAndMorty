package com.chumikov.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName

data class LocationDto(

    @SerializedName("name")
    val name: String
)
