package br.thiago.moviemdb.data.repository.movie


import br.thiago.moviemdb.data.api.ServiceApi
import br.thiago.moviemdb.data.model.movie.CreditResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.data.model.movie.MovieReviewResponse
import br.thiago.moviemdb.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
): MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int?): MovieResponse {
        return serviceApi.getMovieDetails(
            movieId = movieId
        )
    }

    override suspend fun getCredits(
        movieId: Int?
    ): CreditResponse {
        return serviceApi.getCredits(movieId = movieId)
    }

    override suspend fun getSimilar(movieId: Int?
    ): List<MovieResponse> {
        return serviceApi.getSimilar(
            movieId = movieId
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        movieId: Int?
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            movieId = movieId
        ).results ?: emptyList()
    }

}









