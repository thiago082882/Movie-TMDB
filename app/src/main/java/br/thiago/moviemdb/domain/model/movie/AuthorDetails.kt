package br.thiago.moviemdb.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorDetails(
    val name: String?,
    val username: String?,
    val avatarPath: String?,
    val rating: Int?
): Parcelable
