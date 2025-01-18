package br.thiago.moviemdb.domain.local.usecase

import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.local.repository.MovieLocalRepository
import br.thiago.moviemdb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getMovies().map { movieList ->
            movieList.map { it.toDomain() }
        }
    }

}