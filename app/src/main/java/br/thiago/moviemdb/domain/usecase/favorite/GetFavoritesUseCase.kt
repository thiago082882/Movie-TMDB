package br.thiago.moviemdb.domain.usecase.favorite


import br.thiago.moviemdb.domain.model.favorite.FavoriteMovie
import br.thiago.moviemdb.domain.repository.movie.FavoriteMovieRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {

    suspend operator fun invoke(): List<FavoriteMovie> {
        return repository.getFavorites()
    }

}