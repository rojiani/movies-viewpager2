package com.nrojiani.moviesviewpager2.di

import com.nrojiani.moviesviewpager2.BuildConfig
import com.nrojiani.moviesviewpager2.data.repository.IMoviesRepository
import com.nrojiani.moviesviewpager2.data.repository.MoviesRepository
import com.nrojiani.moviesviewpager2.data.source.IRemoteMovieDataSource
import com.nrojiani.moviesviewpager2.data.source.RemoteMovieDataSource
import com.nrojiani.moviesviewpager2.data.source.RemoteMovieDataSource.Companion.SAMPLE_APIS_HOST
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import timber.log.Timber

object DataModule {

    private fun provideJson(coerceNullOrUnknown: Boolean = false): Json = Json {
        encodeDefaults = false
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        coerceInputValues = coerceNullOrUnknown
    }

    private fun provideBaseUrl(): Url = URLBuilder(
        protocol = URLProtocol.HTTP,
        host = SAMPLE_APIS_HOST
    ).build()

    /**
     * @param enableDefaultValidation See [enableSuccess](https://ktor.io/docs/response-validation.html#default)
     * @param json See [Json](https://kotlin.github.io/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json/index.html)
     */
    fun provideHttpClient(
        enableDefaultValidation: Boolean = true,
        json: Json = provideJson(),
        baseUrl: Url = provideBaseUrl()
    ): HttpClient = HttpClient(Android) {
        expectSuccess = enableDefaultValidation

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d("[ktor] $message")
                }
            }
            level = if (BuildConfig.DEBUG) {
                LogLevel.ALL
            } else {
                LogLevel.HEADERS
            }
        }

        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(baseUrl.toString())
            accept(ContentType.Application.Json)
        }
    }

    fun provideRemoteMoviesDataSource(
        httpClient: HttpClient = provideHttpClient(),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
    ): IRemoteMovieDataSource =
        RemoteMovieDataSource(httpClient, dispatcher)

    fun provideMoviesRepository(
        httpClient: HttpClient = provideHttpClient(),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        remoteMovieDataSource: IRemoteMovieDataSource = provideRemoteMoviesDataSource(
            httpClient,
            dispatcher
        )
    ): IMoviesRepository =
        MoviesRepository(remoteMovieDataSource)
}
