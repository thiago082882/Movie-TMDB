package br.thiago.moviemdb.data.model.movie

import br.thiago.moviemdb.data.model.movie.GenreResponse
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenreResponse>?
)
