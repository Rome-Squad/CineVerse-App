package com.giraffe.home.navigation.main

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object YourCollectionRoute

fun NavController.navigateToYourCollection() {
    navigate(YourCollectionRoute)
}