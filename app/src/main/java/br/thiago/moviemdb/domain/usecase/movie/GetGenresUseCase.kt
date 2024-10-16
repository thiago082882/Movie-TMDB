package br.thiago.moviemdb.domain.usecase.movie


import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.model.movie.Genre
import br.thiago.moviemdb.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?): List<Genre> {
        return repository.getGenres(
            apiKey = apiKey,
            language = language
        ).genres?.map { it.toDomain() } ?: emptyList()
    }

}