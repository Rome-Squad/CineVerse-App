package com.giraffe.presentation.explore.navigation.routes

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal data class CastDetailsRoute(val castId: Int)

internal fun NavController.navigateToCastDetails(castId: Int) {
    navigate(CastDetailsRoute(castId))
}