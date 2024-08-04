package com.chumikov.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chumikov.rickandmorty.data.mappers.CharacterMapper
import com.chumikov.rickandmorty.data.network.RickAndMortyApi
import com.chumikov.rickandmorty.domain.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterPagingDataSource @Inject constructor(
    private val api: RickAndMortyApi,
    private val mapper: CharacterMapper
) : PagingSource<Int, Character>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Character> {
        try {
            // Start refresh at page 1 if undefined.
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
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}