package br.thiago.moviemdb.domain.repository.movie

import androidx.paging.PagingSource
import br.thiago.moviemdb.data.model.movie.BasePaginationRemote
import br.thiago.moviemdb.data.model.movie.GenresResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getGenres(): GenresResponse

    fun getMoviesByGenrePagination(genreId: Int?): PagingSource<Int, MovieResponse>

    suspend fun getMoviesByGenre(genreId: Int?): BasePaginationRemote<List<MovieResponse>>

    fun searchMovies(query: String?): PagingSource<Int, MovieResponse>



}