package com.nrojiani.moviesviewpager2.data.repository

import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.data.source.RemoteMovieDataSource
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.runTest
import net.lachlanmckee.timberjunit.TimberTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRepositoryTest {
    private val mockRemoteDataSource = mockk<RemoteMovieDataSource>()
    private lateinit var repository: MoviesRepository

    @get:Rule
    var logAllAlwaysRule: TimberTestRule = TimberTestRule.logAllAlways()

    @Before
    fun setUp() {
        repository = MoviesRepository(
            remoteDataSource = mockRemoteDataSource,
        )
    }

    @Test
    fun `genres result is cached`() = runTest {
        val fetchedGenres = listOf(Genre.CLASSIC, Genre.WESTERN, Genre.COMEDY)
        coEvery {
            mockRemoteDataSource.fetchGenres()
        } returns fetchedGenres

        val genres: List<Genre> = repository.getGenres()
        genres.shouldBe(fetchedGenres)

        repository.getGenres()
        repository.getGenres()
        repository.getGenres()

        coVerify(exactly = 1) {
            mockRemoteDataSource.fetchGenres()
        }
    }
}
