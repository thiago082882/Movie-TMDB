package br.thiago.moviemdb.domain.usecase.movie


import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.model.movie.Movie
import br.thiago.moviemdb.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int?): Movie {
        return repository.getMovieDetails(
            movieId = movieId
        ).toDomain()
    }

}