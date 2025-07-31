package com.giraffe.explore

import androidx.compose.runtime.Composable

interface ExploreApi {

    @Composable
    fun ExploreContainer(onShowBottomBarChange: (Boolean) -> Unit)
}