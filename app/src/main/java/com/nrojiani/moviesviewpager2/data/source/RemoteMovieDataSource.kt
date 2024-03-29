package com.nrojiani.moviesviewpager2.data.source

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie
import com.nrojiani.moviesviewpager2.di.DataModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteMovieDataSource(
    private val httpClient: HttpClient = DataModule.provideHttpClient(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : IRemoteMovieDataSource {

    override suspend fun fetchMovies(genre: Genre): List<Movie> =
        withContext(dispatcher) {
            httpClient.request("movies/${genre.key}").body<List<Movie>>()
                .map { movie -> movie.withPosterImageWidth(DEFAULT_IMAGE_WIDTH) }
        }

    // No API to get the available genres
    override suspend fun fetchGenres(): List<Genre> =
        withContext(dispatcher) {
            Genre.values().filter { it.available }
        }

    companion object {
        const val SAMPLE_APIS_HOST: String = "api.sampleapis.com"
        const val DEFAULT_IMAGE_WIDTH: Int = 600
    }
}
