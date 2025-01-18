package br.thiago.moviemdb.data.local.repository


import br.thiago.moviemdb.data.local.dao.MovieDao
import br.thiago.moviemdb.data.local.entity.MovieEntity
import br.thiago.moviemdb.domain.local.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
): MovieLocalRepository {

    override fun getMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieId: Int?) {
        movieDao.deleteMovie(movieId)
    }
}