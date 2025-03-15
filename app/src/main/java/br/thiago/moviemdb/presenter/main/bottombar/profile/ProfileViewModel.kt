package br.thiago.moviemdb.presenter.main.bottombar.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.thiago.moviemdb.domain.usecase.user.GetUserUseCase
import br.thiago.moviemdb.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUSerUseCase: GetUserUseCase,
) : ViewModel() {

    fun getUser() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = getUSerUseCase()

            emit(StateView.Success(user))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

}