package com.chumikov.rickandmorty.data.network

import androidx.annotation.IntRange
import com.chumikov.rickandmorty.data.network.dto.CharacterDetailsDto
import com.chumikov.rickandmorty.data.network.dto.CharacterListDto
import com.chumikov.rickandmorty.data.network.dto.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacterPaging(@Query("page") @IntRange(from = 1) page: Int): CharacterListDto

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): CharacterDetailsDto

    @GET("episode/{list}")
    suspend fun getEpisodeList(@Path("list") list: List<Int>): List<EpisodeDto>

}