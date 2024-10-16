package br.thiago.moviemdb.domain.repository.movie

import androidx.paging.PagingSource
import br.thiago.moviemdb.data.model.movie.BasePaginationRemote
import br.thiago.moviemdb.data.model.movie.GenresResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getGenres(apiKey: String,language: String?): GenresResponse

    suspend  fun getMoviesByGenre(apiKey: String,language: String?,genreId: Int?): List<MovieResponse>



}