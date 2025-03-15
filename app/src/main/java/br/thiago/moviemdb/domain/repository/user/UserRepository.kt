package br.thiago.moviemdb.domain.repository.user

import android.net.Uri
import br.thiago.moviemdb.domain.model.user.User

interface UserRepository {

    suspend fun update(user: User)

    suspend fun saveUserImage(uri: Uri): String

    suspend fun getUser(): User
}