package br.thiago.moviemdb.domain.usecase.movie


import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.model.movie.MovieReview
import br.thiago.moviemdb.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int?): List<MovieReview> {
        return repository.getMovieReviews(
            movieId = movieId
        ).map { it.toDomain() }
    }

}