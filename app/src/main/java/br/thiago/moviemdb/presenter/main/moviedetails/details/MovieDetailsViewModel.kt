package br.thiago.moviemdb.presenter.main.moviedetails.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
   // private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
   // private val getCreditsUseCase: GetCreditsUseCase,
   // private val insertMovieUseCase: InsertMovieUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int> = _movieId

//    fun getMovieDetails(movieId: Int?) = liveData(Dispatchers.IO) {
//        try {
//            emit(StateView.Loading())
//
//            val result = getMovieDetailsUseCase.invoke(movieId = movieId)
//
//            emit(StateView.Success(result))
//
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        }
//    }
//
//    fun getCredits(movieId: Int?) = liveData(Dispatchers.IO) {
//        try {
//            emit(StateView.Loading())
//
//            val result = getCreditsUseCase(movieId = movieId)
//
//            emit(StateView.Success(result))
//
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        }
//    }
//
//    fun insertMovie(movie: Movie) = liveData(Dispatchers.IO) {
//        try {
//            emit(StateView.Loading())
//
//            insertMovieUseCase(movie)
//
//            emit(StateView.Success(Unit))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        }
//    }

    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }

}