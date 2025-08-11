package com.giraffe.presentation.explore.util

import android.content.Context
import com.giraffe.presentation.explore.R
import com.giraffe.presentation.explore.screen.discover.SearchTab

fun SearchTab.toTitle(context: Context) = when (this) {
    SearchTab.MOVIES -> context.getString(R.string.movies)
    SearchTab.SERIES -> context.getString(R.string.series)
    SearchTab.ACTORS -> context.getString(R.string.actors)
}

fun Any?.orEmpty(): String = this?.toString() ?: ""
