package com.giraffe.profile.screens.collections.mycollections

import androidx.navigation.NavController
import kotlinx.serialization.Serializable


@Serializable
internal data object MyCollectionsRoute

internal fun NavController.navigateToMyCollections() {
    navigate(MyCollectionsRoute)
}