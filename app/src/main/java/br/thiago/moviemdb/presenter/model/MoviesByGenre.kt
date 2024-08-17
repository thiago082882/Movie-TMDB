package br.thiago.moviemdb.presenter.model

import android.os.Parcelable
import br.thiago.moviemdb.domain.model.movie.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesByGenre(
    val id: Int?,
    val name: String?,
    val movies: List<Movie>?
): Parcelable
