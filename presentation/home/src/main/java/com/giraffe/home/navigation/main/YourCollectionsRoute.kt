package com.giraffe.home.navigation.main

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object YourCollectionsRoute

fun NavController.navigateToYourCollections() {
    navigate(YourCollectionsRoute)
}