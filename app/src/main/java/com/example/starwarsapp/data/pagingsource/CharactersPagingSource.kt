package com.example.starwarsapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.starwarsapp.model.Character
import com.example.starwarsapp.network.ApiServices
import com.example.starwarsapp.utils.Constants.FIRST_PAGE_INDEX

class CharactersPagingSource(private val apiServices: ApiServices, private val search: String) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: FIRST_PAGE_INDEX
        return try {
            val response = apiServices.getCharacters(position)
            val character = response.results

            val filterData = character.filter {
                it.name.contains(search, true)
            }
            val nextKey = position + 1
            val prevKey = if (position == 1) null else position - 1

            LoadResult.Page(data = filterData, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}