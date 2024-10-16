package br.thiago.moviemdb.data.repository.movie

import br.thiago.moviemdb.data.api.ServiceApi
import br.thiago.moviemdb.data.model.movie.GenresResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieRepository {
    override suspend fun getGenres(apiKey: String, language: String?): GenresResponse {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = language
        )
    }

    override suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse> {
        return serviceApi.getMoviesByGenre(
            apiKey = apiKey,
            language = language,
            genreId = genreId
        ).results ?: emptyList()
    }


}









