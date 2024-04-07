package com.chumikov.rickandmorty.data.repository

import com.chumikov.rickandmorty.data.network.RickAndMortyApi
import com.chumikov.rickandmorty.domain.Character
import com.chumikov.rickandmorty.domain.CharacterDetails
import com.chumikov.rickandmorty.domain.Episode
import com.chumikov.rickandmorty.domain.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
): RickAndMortyRepository {

//    override suspend fun getCharacterList(): List<Character> {
//        val dtoObjList = api.getAllCharacters().allCharacters
//        return dtoObjList.map { Character(id = it.id, name = it.name, imageUrl = it.imageUrl) }
//    }

    override suspend fun getCharacterDetails(id: Int): CharacterDetails {
        val dtoObj = api.getCharacterDetails(id)
        val episodeIds = dtoObj.episodeUrls.map {
            it.substringAfterLast("/").toInt() }
        return CharacterDetails(
            id = dtoObj.id, name = dtoObj.name, imageUrl = dtoObj.imageUrl,
            species = dtoObj.species, status = dtoObj.status,
            location = dtoObj.location.name, episodes = episodeIds
        )
    }

    override suspend fun getCharacterEpisodes(list: List<Int>): List<Episode> {
        return api.getEpisodeList(list).map {
            Episode(id = it.id, name = it.name, airDate = it.airDate) }
    }

}