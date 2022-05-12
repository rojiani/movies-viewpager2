package com.nrojiani.moviesviewpager2.data.model

import org.apache.commons.text.WordUtils

/**
 * Supported Genres for https://sampleapis.com/api-list/movies
 *
 * Returning error: `ACTION_ADVENTURE`, `SCIFI_FANTASY`
 */
enum class Genre(val key: String, val available: Boolean = true, val imageUrl: String) {
    ACTION_ADVENTURE(
        "action-adventure",
        available = false,
        imageUrl = "https://mocah.org/thumbnail/72664-Uma-ThurmanKill-Bill-Vol.-1-HD-Wallpaper.jpg"
    ),
    ANIMATION("animation", imageUrl = "https://wallpaperaccess.com/full/1161069.jpg"),
    CLASSIC(
        "classic",
        imageUrl = "https://www.flixwatch.co/wp-content/uploads/60000605-960x540.jpg"
    ),
    COMEDY("comedy", imageUrl = "https://wallpapercave.com/wp/wp5268767.jpg"),
    DRAMA("drama", imageUrl = "https://wallpaperaccess.com/full/2325446.jpg"),
    HORROR("horror", imageUrl = "https://wallpapercave.com/wp/wp6759883.jpg"),
    FAMILY(
        "family",
        imageUrl = "https://purewows3.imgix.net/images/articles/2017_06/Finding-Nemo-clownfish-swimming.jpg"
    ),
    MYSTERY(
        "mystery",
        imageUrl = "https://w0.peakpx.com/wallpaper/877/167/HD-wallpaper-movie-north-by-northwest.jpg"
    ),
    SCIFI_FANTASY(
        "scifi-fantasy",
        available = false,
        imageUrl = "https://images6.alphacoders.com/613/thumb-1920-613328.jpg"
    ),
    WESTERN(
        "western",
        imageUrl = "https://cdn.wallpapersafari.com/86/34/WYGevi.jpg"
    );

    fun displayName(): String = when (this) {
        SCIFI_FANTASY -> "SciFi-Fantasy"
        else -> WordUtils.capitalizeFully(key, '-')
    }
}
