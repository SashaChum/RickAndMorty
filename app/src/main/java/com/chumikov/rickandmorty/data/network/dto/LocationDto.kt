package com.chumikov.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(

    @SerializedName("name")
    val name: String
)
