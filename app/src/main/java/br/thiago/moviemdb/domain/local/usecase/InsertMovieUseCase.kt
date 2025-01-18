package br.thiago.moviemdb.domain.local.usecase


import br.thiago.moviemdb.data.mapper.toEntity
import br.thiago.moviemdb.domain.local.repository.MovieLocalRepository
import br.thiago.moviemdb.domain.model.movie.Movie
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {

    suspend operator fun invoke(movie: Movie) {
        return repository.insertMovie(movie.toEntity())
    }

}