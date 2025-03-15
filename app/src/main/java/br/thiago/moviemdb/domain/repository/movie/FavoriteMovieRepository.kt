package br.thiago.moviemdb.domain.repository.movie

import br.thiago.moviemdb.domain.model.favorite.FavoriteMovie


interface FavoriteMovieRepository {

    suspend fun saveFavorites(favorites: List<FavoriteMovie>)

    suspend fun searchFavorites(query: String): List<FavoriteMovie>

    suspend fun getFavorites(): List<FavoriteMovie>

}