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
    suspend fun getCharacterDetails(@Path("id") id: Int): CharacterDetailsDto

    @GET("episode/{list}")
    suspend fun getEpisodeList(@Path("list") list: List<Int>): List<EpisodeDto>

}