package com.chumikov.rickandmorty.data.network

import com.chumikov.rickandmorty.domain.AllCharacters
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(): AllCharacters

}