package com.example.seminar_assignment_2025.data

import com.example.seminar_assignment_2025.data.remote.MovieApiService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("backdrop_path") val backdropPath: String?,
    val overview: String?,
    val popularity: Double
)

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRepository {

    private val genreMap: Map<Int, String> = mapOf(
        28 to "Action", 12 to "Adventure", 16 to "Animation", 35 to "Comedy", 80 to "Crime",
        99 to "Documentary", 18 to "Drama", 10751 to "Family", 14 to "Fantasy", 36 to "History",
        27 to "Horror", 10402 to "Music", 9648 to "Mystery", 10749 to "Romance", 878 to "Science Fiction",
        10770 to "TV Movie", 53 to "Thriller", 10752 to "War", 37 to "Western"
    )

    override suspend fun searchByTitle(query: String): List<Movie> {
        return movieApiService.searchMovies(query).results.map { dto ->
            Movie(
                id = dto.id,
                title = dto.title,
                releaseDate = dto.releaseDate,
                voteAverage = dto.voteAverage,
                posterPath = dto.posterPath ?: "",
                genreIds = dto.genreIds,
                backdropPath = dto.backdropPath ?: "",
                overview = dto.overview ?: "",
                popularity = dto.popularity
            )
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Movie {
        val dto = movieApiService.getMovieDetail(movieId)
        return Movie(
            id = dto.id,
            title = dto.title,
            releaseDate = dto.releaseDate,
            voteAverage = dto.voteAverage,
            posterPath = dto.posterPath ?: "",
            genreIds = dto.genreIds,
            backdropPath = dto.backdropPath ?: "",
            overview = dto.overview ?: "",
            popularity = dto.popularity
        )
    }

    override fun getGenreName(id: Int): String {
        return genreMap[id] ?: ""
    }
}