package com.nrojiani.moviesviewpager2.data

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>() {
        override fun toString(): String = "Resource.Loading"
    }

    /**
     * Return the data if the Resource's state is [Resource.Success], or null
     * if [Resource.Loading] or [Resource.Failure].
     */
    fun dataOrNull(): T? = (this as? Success<T>)?.data

    @Suppress("UNCHECKED_CAST")
    fun dataOrEmptyList(): List<T> = (this as? Success<List<T>>)?.data
        ?: emptyList()

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is Success<T>

    val isFailure: Boolean
        get() = this is Failure

    companion object {
        suspend fun <T> from(retrieveResource: suspend () -> T): Resource<T> =
            kotlin.runCatching {
                retrieveResource()
            }.fold(
                onSuccess = { Success(it) },
                onFailure = { e -> Failure(e) }
            )
    }
}
