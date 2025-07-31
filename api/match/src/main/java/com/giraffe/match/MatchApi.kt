package com.giraffe.match

import androidx.compose.runtime.Composable

interface MatchApi {

    @Composable
    fun MatchContainer(onShowBottomBarChange: (Boolean) -> Unit)
}