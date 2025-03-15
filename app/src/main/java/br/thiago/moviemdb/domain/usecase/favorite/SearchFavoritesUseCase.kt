package br.thiago.moviemdb.domain.usecase.favorite


import br.thiago.moviemdb.domain.model.favorite.FavoriteMovie
import br.thiago.moviemdb.domain.repository.movie.FavoriteMovieRepository
import javax.inject.Inject

class SearchFavoritesUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {

    suspend operator fun invoke(query: String): List<FavoriteMovie> {
        return repository.searchFavorites(query)
    }

}