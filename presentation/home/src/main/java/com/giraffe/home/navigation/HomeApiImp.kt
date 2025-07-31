package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.HomeApi
import javax.inject.Inject

class HomeApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi
) : HomeApi {
    @Composable
    override fun HomeContainer() {
        val navController: NavHostController = rememberNavController()
        HomeNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
        )
    }
}