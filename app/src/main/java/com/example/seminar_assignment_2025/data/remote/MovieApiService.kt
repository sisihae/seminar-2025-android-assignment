package com.example.seminar_assignment_2025.data.remote

import com.example.seminar_assignment_2025.data.Movie
import com.example.seminar_assignment_2025.data.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): SearchResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDto
}

@kotlinx.serialization.Serializable
data class SearchResponse(
    val results: List<MovieDto>
)
