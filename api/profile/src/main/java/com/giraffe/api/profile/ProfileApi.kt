package com.giraffe.api.profile

import androidx.compose.runtime.Composable

interface ProfileApi {

    @Composable
    fun CollectionContainer(
        collectionId: Int,
        collectionName: String,
        navigateBack: () -> Unit,
    )

    @Composable
    fun YourCollectionsContainer(
        navigateBack: () -> Unit,
    )

    @Composable
    fun ProfileContainer(onShowBottomBarChange: (Boolean) -> Unit)
}