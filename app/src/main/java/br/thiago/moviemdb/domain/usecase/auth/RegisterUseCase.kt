package br.thiago.moviemdb.domain.usecase.auth

import br.thiago.moviemdb.domain.repository.auth.FirebaseAuthentication
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val firebaseAuthentication: FirebaseAuthentication
) {

    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthentication.register(email, password)
    }

}