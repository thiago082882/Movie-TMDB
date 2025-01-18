package br.thiago.moviemdb.presenter.main.moviedetails.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.thiago.moviemdb.domain.usecase.movie.GetSimilarUseCase
import br.thiago.moviemdb.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val getSimilarUseCase: GetSimilarUseCase
) : ViewModel() {

    fun getSimilar(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movies = getSimilarUseCase(movieId = movieId)

            emit(StateView.Success(movies))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

}