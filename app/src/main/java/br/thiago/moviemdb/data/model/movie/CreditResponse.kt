package br.thiago.moviemdb.data.model.movie

import com.google.gson.annotations.SerializedName

data class CreditResponse(

    @SerializedName("cast")
    val cast: List<PersonResponse>?

)
