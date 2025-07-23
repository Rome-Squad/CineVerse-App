package com.giraffe.details

import androidx.compose.runtime.Composable

interface DetailsApi {
    @Composable
    fun DetailsContainer(
        startDestination: DetailsStartDestination,
        backPress: () -> Unit
    )

}