package com.example.seminar_assignment_2025.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResponse(
    val results: List<MovieListItemDto>
)

@Serializable
data class MovieListItemDto(
    val id: Int,
    val title: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("backdrop_path") val backdropPath: String?,
    val overview: String?,
    val popularity: Double,
    val adult: Boolean
)

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("poster_path") val posterPath: String?,
    val genres: List<GenreDto>,
    @SerialName("backdrop_path") val backdropPath: String?,
    val overview: String?,
    val popularity: Double,
    val adult: Boolean,
    val runtime: Int? = null,
    @SerialName("original_title") val originalTitle: String,
    val status: String?,
    val budget: Long?,
    val revenue: Long?
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)
