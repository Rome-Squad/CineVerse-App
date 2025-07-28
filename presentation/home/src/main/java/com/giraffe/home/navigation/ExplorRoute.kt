package com.giraffe.home.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal object DiscoverRoute

internal fun NavController.navigateToDiscover() {
    navigate(DiscoverRoute)
}
