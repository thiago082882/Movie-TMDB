package br.thiago.moviemdb.presenter.main.bottombar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.thiago.moviemdb.domain.usecase.favorite.GetFavoritesUseCase
import br.thiago.moviemdb.domain.usecase.favorite.SearchFavoritesUseCase
import br.thiago.moviemdb.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val searchFavoritesUseCase: SearchFavoritesUseCase
) : ViewModel() {

    fun getFavorites() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val favorites = getFavoritesUseCase()

            emit(StateView.Success(favorites))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

    fun searchFavorites(query: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val favorites = searchFavoritesUseCase(query)

            emit(StateView.Success(favorites))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

}