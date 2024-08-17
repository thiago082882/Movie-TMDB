package br.thiago.moviemdb.domain.repository.user

import br.thiago.moviemdb.domain.model.user.User

interface UserRepository {

    suspend fun update(user: User)

    suspend fun getUser(): User

}