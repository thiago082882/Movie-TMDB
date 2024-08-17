package br.thiago.moviemdb.domain.model.movie

import android.os.Parcelable
import br.thiago.moviemdb.domain.model.movie.AuthorDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieReview(
    val author: String?,
    val authorDetails: AuthorDetails?,
    val content: String?,
    val createdAt: String?,
    val id: String?,
    val updatedAt: String?,
    val url: String?
) : Parcelable
