package com.nrojiani.moviesviewpager2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Parcelize
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @JsonNames("posterURL")
    val posterUrl: String,
    val imdbId: String,
) : Parcelable {
    fun withPosterImageWidth(width: Int): Movie =
        copy(posterUrl = posterUrl.replace("SX300", "SX$width"))
}
