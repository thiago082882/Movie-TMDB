package br.thiago.moviemdb.domain.usecase.auth

import br.thiago.moviemdb.domain.repository.auth.FirebaseAuthentication
import javax.inject.Inject

class ForgotUseCase @Inject constructor(
    private val firebaseAuthentication: FirebaseAuthentication
) {

    suspend operator fun invoke(email: String) {
        firebaseAuthentication.forgot(email)
    }

}