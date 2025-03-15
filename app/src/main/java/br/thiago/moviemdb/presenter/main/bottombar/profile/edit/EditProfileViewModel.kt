package br.thiago.moviemdb.presenter.main.bottombar.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.thiago.moviemdb.domain.model.user.User
import br.thiago.moviemdb.domain.usecase.user.GetUserUseCase
import br.thiago.moviemdb.domain.usecase.user.UserUpdateUseCase
import br.thiago.moviemdb.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


import android.net.Uri
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.liveData
import br.thiago.moviemdb.R
import br.thiago.moviemdb.domain.usecase.user.SaveUserImageUseCase


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUpdateUseCase: UserUpdateUseCase,
    private val getUSerUseCase: GetUserUseCase,
    private val saveUserImageUseCase: SaveUserImageUseCase
) : ViewModel() {

    private val _validateData = MutableLiveData<Pair<Boolean, Int?>>()
    val validateData: MutableLiveData<Pair<Boolean, Int?>> = _validateData

    fun update(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            userUpdateUseCase(user)

            emit(StateView.Success(Unit))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

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

    fun saveUserImage(uri: Uri) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val url = saveUserImageUseCase(uri)

            emit(StateView.Success(url))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

    fun validateData(
        name: String,
        surName: String,
        phone: String,
        genre: String,
        country: String
    ) {
        if (name.isEmpty()) {
            validateData.value = Pair(false, R.string.text_name_empty_edit_profile_fragment)
            return
        }

        if (surName.isEmpty()) {
            validateData.value = Pair(false, R.string.text_surname_empty_edit_profile_fragment)
            return
        }

        if (phone.isEmpty()) {
            validateData.value = Pair(false, R.string.text_phone_empty_edit_profile_fragment)
            return
        }

        if (genre.isEmpty()) {
            validateData.value = Pair(false, R.string.text_genre_empty_edit_profile_fragment)
            return
        }

        if (country.isEmpty()) {
            validateData.value = Pair(false, R.string.text_country_empty_edit_profile_fragment)
            return
        }

        validateData.value = Pair(true, null)
    }

}