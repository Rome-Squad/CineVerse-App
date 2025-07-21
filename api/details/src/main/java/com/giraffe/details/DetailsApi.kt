package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface DetailsApi {
    @Composable
    fun DetailsContainer(
        modifier: Modifier,
        startDestination: DetailsStartDestination
    )
}