package com.chumikov.rickandmorty.data.network

import com.chumikov.rickandmorty.data.network.model.CharacterDetailsDto
import com.chumikov.rickandmorty.data.network.model.CharacterListDto
import com.chumikov.rickandmorty.data.network.model.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(): CharacterListDto

    @GET("character/{id}")
    suspend fun getCharacterInfo(@Path("id") id: Int): CharacterDetailsDto

    @GET("episode/{array}")
    suspend fun getEpisodeList(@Path("array") array: String): List<EpisodeDto>

}