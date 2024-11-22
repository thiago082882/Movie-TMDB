package br.thiago.moviemdb.data.repository.movie

import androidx.paging.PagingSource
import br.thiago.moviemdb.data.api.ServiceApi
import br.thiago.moviemdb.data.model.movie.BasePaginationRemote
import br.thiago.moviemdb.data.model.movie.GenresResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.data.paging.MovieByGenrePagingSource
import br.thiago.moviemdb.data.paging.SearchMoviePagingSource
import br.thiago.moviemdb.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieRepository {

    override suspend fun getGenres(): GenresResponse {
        return serviceApi.getGenres()
    }

    override fun getMoviesByGenrePagination(genreId: Int?): PagingSource<Int, MovieResponse> {
        return MovieByGenrePagingSource(serviceApi, genreId)
    }

    override suspend fun getMoviesByGenre(genreId: Int?): BasePaginationRemote<List<MovieResponse>> {
        return serviceApi.getMoviesByGenre(genreId)
    }

    override fun searchMovies(query: String?): PagingSource<Int, MovieResponse> {
        return SearchMoviePagingSource(serviceApi, query)
    }

}













