package br.thiago.moviemdb.domain.local.repository


import br.thiago.moviemdb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {

    fun getMovies(): Flow<List<MovieEntity>>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieId: Int?)

}