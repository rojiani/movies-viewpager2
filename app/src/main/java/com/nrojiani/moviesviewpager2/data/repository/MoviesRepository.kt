package com.nrojiani.moviesviewpager2.data.repository

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie
import com.nrojiani.moviesviewpager2.data.source.IRemoteMovieDataSource

class MoviesRepository(
    private val remoteDataSource: IRemoteMovieDataSource
) : IMoviesRepository {

    override suspend fun getGenres(): List<Genre> =
        remoteDataSource.fetchGenres()

    override suspend fun getMoviesByGenre(genre: String): List<Movie> =
        remoteDataSource.fetchMovies(genre)
}
