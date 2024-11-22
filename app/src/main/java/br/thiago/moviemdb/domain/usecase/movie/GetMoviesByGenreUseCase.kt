package br.thiago.moviemdb.domain.usecase.movie

import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.domain.model.movie.Movie
import br.thiago.moviemdb.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(genreId: Int?): List<MovieResponse> {
        return repository.getMoviesByGenre(genreId).results ?: emptyList()
    }




}