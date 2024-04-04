package com.chumikov.rickandmorty.data.network.model

import com.chumikov.rickandmorty.data.network.RickAndMortyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyApiFactory {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
}