package com.nrojiani.moviesviewpager2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: String,
    val title: String,
    @Json(name = "posterURL")
    val posterUrl: String,
    val imdbId: String,
)