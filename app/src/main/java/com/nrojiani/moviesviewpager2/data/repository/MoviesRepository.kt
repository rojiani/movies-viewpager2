package com.nrojiani.moviesviewpager2.data.repository

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie
import com.nrojiani.moviesviewpager2.data.source.IRemoteMovieDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

class MoviesRepository(
    private val remoteDataSource: IRemoteMovieDataSource,
) : IMoviesRepository {

    // in-memory cache, from https://developer.android.com/topic/architecture/data-layer
    private val genresMutex = Mutex()
    private var genres: List<Genre> = emptyList()

    private val moviesMutex = Mutex()
    private var moviesCache: Map<Genre, List<Movie>> = emptyMap()

    override suspend fun getGenres(): List<Genre> {
        if (genres.isEmpty()) {
            val result = remoteDataSource.fetchGenres()
            genresMutex.withLock {
                genres = result
            }
        }

        return genresMutex.withLock { this.genres }
    }

    override suspend fun getMoviesByGenre(genre: Genre): List<Movie> {
        Timber.d("getMoviesByGenre(genre: ${genre.key})")
        Timber.d("cached?: ${genre in moviesCache}")
        if (genre !in moviesCache) {
            val result = remoteDataSource.fetchMovies(genre)
            moviesMutex.withLock {
                moviesCache = moviesCache + mapOf(genre to result)
            }
        }

        return moviesMutex.withLock { this.moviesCache.getValue(genre) }
    }
}
