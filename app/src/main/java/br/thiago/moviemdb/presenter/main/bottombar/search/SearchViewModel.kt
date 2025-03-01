package br.thiago.moviemdb.presenter.main.bottombar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.thiago.moviemdb.domain.model.movie.Movie
import br.thiago.moviemdb.domain.usecase.movie.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Movie>?>(null)
    val searchResults: StateFlow<PagingData<Movie>?> = _searchResults.asStateFlow()

    fun searchMovies(query: String?) {
        viewModelScope.launch {
            searchMoviesUseCase(query).cachedIn(viewModelScope).collectLatest { pagingData ->
                _searchResults.value = pagingData
            }
        }
    }
}
