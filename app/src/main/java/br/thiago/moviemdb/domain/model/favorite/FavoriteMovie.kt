package br.thiago.moviemdb.domain.model.favorite

import android.os.Parcelable
import br.thiago.moviemdb.domain.model.movie.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteMovie(
    val genres: List<Genre>? = null,
    val id: Int? = null,
    val posterPath: String? = null,
    val title: String? = null,
    val voteAverage: String? = null
) : Parcelable