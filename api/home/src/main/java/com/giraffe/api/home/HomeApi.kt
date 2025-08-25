package com.giraffe.api.home


import android.content.Context

interface HomeApi {

    fun launchHome(context: Context)
    fun navigateToExploreScreen()
}