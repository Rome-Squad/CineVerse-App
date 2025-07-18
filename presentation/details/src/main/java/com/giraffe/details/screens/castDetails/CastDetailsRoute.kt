package com.giraffe.details.screens.castDetails

import androidx.navigation.NavController
import com.giraffe.details.navigation.CastDetailsRoute

fun NavController.navigateToPersonDetails(personID: Int) {
    navigate(CastDetailsRoute(personId = personID))
}