package com.giraffe.explore.util

import android.content.Context
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.presentation.explore.R

fun SearchTab.toTitle(context: Context) = when (this) {
    SearchTab.MOVIES -> context.getString(R.string.movies)
    SearchTab.SERIES -> context.getString(R.string.series)
    SearchTab.ACTORS -> context.getString(R.string.actors)
}

fun Any?.orEmpty(): String = this?.toString() ?: ""
