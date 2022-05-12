package com.nrojiani.moviesviewpager2.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nrojiani.moviesviewpager2.data.Resource
import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie
import com.nrojiani.moviesviewpager2.data.repository.IMoviesRepository
import com.nrojiani.moviesviewpager2.di.DataModule
import com.nrojiani.moviesviewpager2.utils.flow.WhileViewSubscribed
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MoviesViewModel(
    selectedGenre: Genre,
    private val repository: IMoviesRepository = DataModule.provideMoviesRepository()
) : ViewModel() {

    private val _genre = MutableStateFlow(selectedGenre)
    val genre: StateFlow<Genre>
        get() = _genre.asStateFlow()

    val movies: StateFlow<Resource<List<Movie>>> = genre.mapLatest {
        Timber.d("movies mapLatest genre ($it) flow")
        Resource.from {
            repository.getMoviesByGenre(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = Resource.Loading
    )

    class Factory(
        private val selectedGenre: Genre,
        private val repository: IMoviesRepository = DataModule.provideMoviesRepository()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(selectedGenre, repository) as T
        }
    }
}
