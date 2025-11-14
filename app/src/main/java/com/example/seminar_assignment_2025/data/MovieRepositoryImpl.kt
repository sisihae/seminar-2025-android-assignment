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
        28 to "액션", 12 to "모험", 16 to "애니메이션", 35 to "코미디", 80 to "범죄",
        99 to "다큐멘터리", 18 to "드라마", 10751 to "가족", 14 to "판타지", 36 to "역사",
        27 to "공포", 10402 to "음악", 9648 to "미스터리", 10749 to "로맨스", 878 to "SF",
        10770 to "TV 영화", 53 to "스릴러", 10752 to "전쟁", 37 to "서부"
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