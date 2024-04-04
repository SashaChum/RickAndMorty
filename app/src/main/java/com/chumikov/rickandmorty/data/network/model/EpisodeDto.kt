package com.chumikov.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName

data class EpisodeDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("air_date")
    val airDate: String

)
