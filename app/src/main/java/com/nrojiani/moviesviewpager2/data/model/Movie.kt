package com.nrojiani.moviesviewpager2.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @JsonNames("posterURL")
    val posterUrl: String,
    val imdbId: String,
)
