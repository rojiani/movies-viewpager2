package com.nrojiani.moviesviewpager2

import android.app.Application
import timber.log.Timber

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTreeWithThreadName())
    }

    class DebugTreeWithThreadName : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return "${super.createStackElementTag(element)} [${Thread.currentThread().name}]"
        }
    }
}
