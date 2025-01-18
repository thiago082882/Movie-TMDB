package br.thiago.moviemdb.domain.usecase.movie

import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.domain.model.movie.Credit
import br.thiago.moviemdb.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject


class GetCreditsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int?): Credit {
        return repository.getCredits(movieId = movieId).toDomain()
    }

}