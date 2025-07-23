package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.home.HomeApi

class HomeApiImp(
    private val detailsApi: DetailsApi
) : HomeApi {
    @Composable
    override fun HomeContainer() {
        val navController = rememberNavController()
        HomeNavGraph(
            navController = navController,
            detailsApi = detailsApi
        )
    }
}