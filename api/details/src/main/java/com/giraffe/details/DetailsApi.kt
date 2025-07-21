package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// the api represent cross feature interaction
interface DetailsApi {
    @Composable
    fun DetailsContainer(
        modifier: Modifier,
        startDestination: DetailsStartDestination,
        backPress: () -> Unit
    )

}