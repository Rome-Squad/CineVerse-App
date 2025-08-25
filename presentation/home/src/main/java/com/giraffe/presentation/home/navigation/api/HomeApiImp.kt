package com.giraffe.presentation.home.navigation.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.home.navigation.main.HomeActivity
import com.giraffe.presentation.home.navigation.main.routes.navigateToExplore
import javax.inject.Inject

class HomeApiImp @Inject constructor() : HomeApi {
    private var navController: NavHostController? = null

    override fun launchHome(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        if (context is Activity) context.finish()
    }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    override fun navigateToExploreScreen() {
        navController?.navigateToExplore()
    }
}