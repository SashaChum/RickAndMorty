package com.chumikov.rickandmorty.domain

import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(): AllCharacters

}