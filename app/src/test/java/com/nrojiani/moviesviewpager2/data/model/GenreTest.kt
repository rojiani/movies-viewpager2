package com.nrojiani.moviesviewpager2.data.model

import io.kotest.matchers.shouldBe
import org.junit.Test

class GenreTest {
    @Test
    fun displayName() {
        Genre.CLASSIC.displayName().shouldBe("Classic")
        Genre.SCIFI_FANTASY.displayName().shouldBe("SciFi-Fantasy")
        Genre.ACTION_ADVENTURE.displayName().shouldBe("Action-Adventure")
    }
}
