package br.thiago.moviemdb.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credit(

    val cast: List<Person>?

) : Parcelable
