package com.giraffe.cineverseapp.nav

import com.giraffe.details.DetailsStartDestination

sealed class AppScreen {
    object Explore : AppScreen()
    data class Details(val startDestination: DetailsStartDestination) : AppScreen()
    object Authentication : AppScreen()
}
