package com.example.starwarsapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.starwarsapp.model.*
import com.example.starwarsapp.network.ApiServices
import com.example.starwarsapp.network.SafeApiCall
import com.example.starwarsapp.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val apiServices: ApiServices) :
    SafeApiCall() {

    fun getCharacter(search: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                C
            }
        )
    }

}