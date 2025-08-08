package com.giraffe.home.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object YourCollectionRoute

fun NavController.navigateToYourCollection() {
    navigate(YourCollectionRoute)
}