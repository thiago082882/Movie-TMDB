package br.thiago.moviemdb.domain.usecase.movie


import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.model.movie.Movie
import br.thiago.moviemdb.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetSimilarUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int?): List<Movie> {
        return repository.getSimilar(
            movieId = movieId
        ).map { it.toDomain() }
            .filter { it.posterPath != null }
    }

}