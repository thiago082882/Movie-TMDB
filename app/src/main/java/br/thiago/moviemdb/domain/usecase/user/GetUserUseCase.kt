package br.thiago.moviemdb.domain.usecase.user

import br.thiago.moviemdb.domain.model.user.User
import br.thiago.moviemdb.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): User {
        return userRepository.getUser()
    }

}