package com.giraffe.profile

import androidx.compose.runtime.Composable

interface ProfileApi {

    @Composable
    fun CollectionContainer(
        collectionId: Int,
        collectionName: String,
        navigateBack: () -> Unit,
        onShowBottomBarChange: (Boolean) -> Unit,
    )

    @Composable
    fun YourCollectionsContainer(
        onShowBottomBarChange: (Boolean) -> Unit,
        navigateBack: () -> Unit,
    )

    @Composable
    fun ProfileContainer(onShowBottomBarChange: (Boolean) -> Unit)
}