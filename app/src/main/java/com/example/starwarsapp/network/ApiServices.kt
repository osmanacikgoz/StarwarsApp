package com.example.starwarsapp.network

import com.example.starwarsapp.model.FilmResponse
import com.example.starwarsapp.model.HomeWorldResponse
import com.example.starwarsapp.model.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServices {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): PeopleResponse

    @GET
    suspend fun getFilm(@Url url: String): FilmResponse

    @GET
    suspend fun getHomeWorld(@Url url: String): HomeWorldResponse
}