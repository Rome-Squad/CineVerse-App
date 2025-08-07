package com.giraffe.profile

import androidx.compose.runtime.Composable

interface ProfileApi {

    @Composable
    fun MyCollectionsContainer(
        onShowBottomBarChange: (Boolean) -> Unit
    )

    @Composable
    fun ProfileContainer(onShowBottomBarChange: (Boolean) -> Unit)
}