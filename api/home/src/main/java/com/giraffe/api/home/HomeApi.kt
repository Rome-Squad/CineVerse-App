package com.giraffe.api.home


import android.content.Context
import androidx.compose.runtime.Composable

interface HomeApi {

    fun launchHome(context: Context)
    fun navigateToExploreScreen()
}