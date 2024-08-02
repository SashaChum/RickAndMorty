package com.chumikov.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chumikov.rickandmorty.data.mappers.CharacterMapper
import com.chumikov.rickandmorty.data.network.RickAndMortyApi
import com.chumikov.rickandmorty.domain.Character
import javax.inject.Inject

class CharacterPagingDataSource @Inject constructor(
    private val api: RickAndMortyApi,
    private val mapper: CharacterMapper
) : PagingSource<Int, Character>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Character> {
        try {
            val currentPageNumber = params.key ?: 1
            val characterListDto = api.getCharacterPaging(currentPageNumber)
            val nextPageNumber = currentPageNumber + 1
            return LoadResult.Page(
                data = characterListDto.allCharacters.map {
                    mapper.toDomain(it)
                },
                prevKey = null, // Only paging forward.
                nextKey = if (nextPageNumber <= characterListDto.pageInfo.pages) {
                    nextPageNumber
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}