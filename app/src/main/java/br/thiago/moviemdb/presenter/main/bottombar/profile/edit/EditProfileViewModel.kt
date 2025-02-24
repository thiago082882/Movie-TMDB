package br.thiago.moviemdb.presenter.main.bottombar.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.thiago.moviemdb.domain.model.user.User
import br.thiago.moviemdb.domain.usecase.user.GetUserUseCase
import br.thiago.moviemdb.domain.usecase.user.UserUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class EditProfileViewModel constructor(
    private val userUpdateUseCase: UserUpdateUseCase,
    private val getUSerUseCase: GetUserUseCase
) : ViewModel() {

//    fun update(user: User) = liveData(Dispatchers.IO) {
//        try {
//            emit(StateView.Loading())
//
//            userUpdateUseCase(user)
//
//            emit(StateView.Success(Unit))
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//            emit(StateView.Error(message = exception.message))
//        }
//    }
//
//    fun getUser() = liveData(Dispatchers.IO) {
//        try {
//            emit(StateView.Loading())
//
//            val user = getUSerUseCase()
//
//            emit(StateView.Success(user))
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//            emit(StateView.Error(message = exception.message))
//        }
//    }

}