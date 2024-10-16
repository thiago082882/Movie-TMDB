package br.thiago.moviemdb.domain.repository.movie

import br.thiago.moviemdb.data.model.movie.CreditResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.data.model.movie.MovieReviewResponse

interface MovieDetailsRepository {

    suspend fun getMovieDetails(movieId: Int?): MovieResponse

    suspend fun getCredits(movieId: Int?): CreditResponse

    suspend fun getSimilar(movieId: Int?): List<MovieResponse>

    suspend fun getMovieReviews(movieId: Int?): List<MovieReviewResponse>

}