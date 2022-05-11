package com.nrojiani.moviesviewpager2.data.source

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.model.Movie
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.string.shouldContain
import io.ktor.client.plugins.*
import kotlinx.coroutines.test.runTest
import net.lachlanmckee.timberjunit.TimberTestRule
import org.junit.Rule
import org.junit.Test

class RemoteMovieDataSourceTest {
    private val remoteMovieDataSource: IRemoteMovieDataSource = RemoteMovieDataSource()

    @get:Rule
    var logAllAlwaysRule: TimberTestRule = TimberTestRule.logAllAlways()

    @Test
    fun `fetchMovies by genre`() = runTest {
        validateWesternMovies(remoteMovieDataSource.fetchMovies("western"))
    }

    @Test
    fun `fetchMovies by enum genre`() = runTest {
        validateWesternMovies(remoteMovieDataSource.fetchMovies(Genre.WESTERN))
    }

    @Test
    fun `fetchMovies with server error`() = runTest {
        shouldThrow<ServerResponseException> {
            remoteMovieDataSource.fetchMovies(Genre.SCIFI_FANTASY)
        }.message
            .shouldContain("Internal Server Error")
    }

    @Test
    fun fetchGenres() = runTest {
        remoteMovieDataSource.fetchGenres()
            .shouldContain(listOf(Genre.CLASSIC, Genre.WESTERN, Genre.ANIMATION))
            .shouldNotContain(listOf(Genre.ACTION_ADVENTURE, Genre.SCIFI_FANTASY))
    }

    private fun validateWesternMovies(westerns: List<Movie>) {
        westerns.shouldNotBeEmpty()
            .shouldHaveAtLeastSize(60)
            .shouldContain(
                Movie(
                    id = 9,
                    title = "The Wild Bunch",
                    posterUrl = "https://m.media-amazon.com/images/M/MV5BNGUyYTZmOWItMDJhMi00N2IxLW" +
                        "IyNDMtNjUxM2ZiYmU5YWU1XkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg",
                    imdbId = "tt0065214"
                )
            )
    }
}
