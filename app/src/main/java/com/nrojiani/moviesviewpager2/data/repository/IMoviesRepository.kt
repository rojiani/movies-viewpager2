package com.nrojiani.moviesviewpager2.data.repository

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie

interface IMoviesRepository {
    suspend fun getGenres(): List<Genre>

    suspend fun getMoviesByGenre(genre: String): List<Movie>
}
