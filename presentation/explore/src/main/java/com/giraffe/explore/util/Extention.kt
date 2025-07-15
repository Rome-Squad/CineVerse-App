package com.giraffe.explore.util

import android.content.Context
import com.giraffe.explore.SearchTab
import com.giraffe.media.explore.R

fun SearchTab.toTitle(context: Context) = when (this) {
    SearchTab.MOVIES -> context.getString(R.string.movies)
    SearchTab.SERIES -> context.getString(R.string.series)
    SearchTab.ACTORS -> context.getString(R.string.actors)
}