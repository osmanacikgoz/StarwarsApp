package com.example.starwarsapp.model


import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("characters")
        val characters: List<String>,
        @SerializedName("created")
        val created: String,
        @SerializedName("director")
        val director: String,
        @SerializedName("edited")
        val edited: String,
        @SerializedName("episode_id")
        val episodeId: Int,
        @SerializedName("opening_crawl")
        val openingCrawl: String,
        @SerializedName("planets")
        val planets: List<String>,
        @SerializedName("producer")
        val producer: String,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("species")
        val species: List<String>,
        @SerializedName("starships")
        val starships: List<String>,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("vehicles")
        val vehicles: List<String>
    )
}