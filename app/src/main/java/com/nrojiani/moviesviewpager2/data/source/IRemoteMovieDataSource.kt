package com.nrojiani.moviesviewpager2.data.source

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie

interface IRemoteMovieDataSource {

    suspend fun fetchGenres(): List<Genre>

    suspend fun fetchMovies(genre: String): List<Movie>

    suspend fun fetchMovies(genre: Genre): List<Movie>
}
