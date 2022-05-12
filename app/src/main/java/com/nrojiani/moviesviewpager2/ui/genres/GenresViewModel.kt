package com.nrojiani.moviesviewpager2.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nrojiani.moviesviewpager2.data.Resource
import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.repository.IMoviesRepository
import com.nrojiani.moviesviewpager2.di.DataModule
import com.nrojiani.moviesviewpager2.utils.flow.WhileViewSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class GenresViewModel(
    private val moviesRepository: IMoviesRepository = DataModule.provideMoviesRepository()
) : ViewModel() {

    val genres: StateFlow<Resource<List<Genre>>> = flow {
        Timber.d("starting genres flow")
        val genres: Resource<List<Genre>> = kotlin.runCatching {
            moviesRepository.getGenres()
        }.map {
            Resource.Success(it)
        }.getOrElse { Resource.Failure(it) }

        emit(genres)
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = Resource.Loading
    )
}
