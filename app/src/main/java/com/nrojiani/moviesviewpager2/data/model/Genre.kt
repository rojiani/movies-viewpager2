package com.nrojiani.moviesviewpager2.data.model

import org.apache.commons.text.WordUtils

/**
 * Supported Genres for https://sampleapis.com/api-list/movies
 *
 * Returning error: `ACTION_ADVENTURE`, `SCIFI_FANTASY`
 */
enum class Genre(val key: String, val available: Boolean = true) {
    ACTION_ADVENTURE("action-adventure", available = false),
    ANIMATION("animation"),
    CLASSIC("classic"),
    COMEDY("comedy"),
    DRAMA("drama"),
    HORROR("horror"),
    FAMILY("family"),
    MYSTERY("mystery"),
    SCIFI_FANTASY("scifi-fantasy", available = false),
    WESTERN("western");

    fun displayName(): String = when (this) {
        SCIFI_FANTASY -> "SciFi-Fantasy"
        else -> WordUtils.capitalizeFully(key, '-')
    }
}
