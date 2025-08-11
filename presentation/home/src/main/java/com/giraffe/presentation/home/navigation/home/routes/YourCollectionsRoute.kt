package com.giraffe.presentation.home.navigation.home.routes

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object YourCollectionsRoute

fun NavController.navigateToYourCollections() {
    navigate(YourCollectionsRoute)
}