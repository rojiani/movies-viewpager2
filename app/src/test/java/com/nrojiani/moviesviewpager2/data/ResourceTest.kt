package com.nrojiani.moviesviewpager2.data

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class ResourceTest {

    @Test
    fun dataOrNull() {
        Resource.Success(7).dataOrNull()
            .shouldBe(7)

        val loading: Resource<Int> = Resource.Loading
        loading.dataOrNull().shouldBeNull()

        val err: Resource<Int> = Resource.Failure(IOException("some error"))
        err.dataOrNull().shouldBeNull()
    }

    @Test
    fun dataOrEmptyList() {
        Resource.Success(listOf("a", "b", "c")).dataOrEmptyList()
            .shouldBe(listOf("a", "b", "c"))

        val loading: Resource<Int> = Resource.Loading
        loading.dataOrEmptyList().shouldBeEmpty()

        val err: Resource<Int> = Resource.Failure(IOException("some error"))
        err.dataOrEmptyList().shouldBeEmpty()
    }

    @Test
    fun from() = runTest {
        val resource: Resource<List<Int>> = Resource.from {
            fetchList(coroutineContext)
        }

        resource.isSuccess.shouldBeTrue()
        resource.dataOrEmptyList().shouldBe(listOf(0, 1, 2, 3, 4, 5, 6, 7))
    }

    private suspend fun fetchList(context: CoroutineContext): List<Int> =
        withContext(context) {
            delay(3_000L)
            (0..7).toList()
        }

}