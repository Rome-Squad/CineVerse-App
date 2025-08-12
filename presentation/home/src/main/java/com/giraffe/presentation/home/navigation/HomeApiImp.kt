package com.giraffe.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.home.HomeApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.match.MatchApi
import com.giraffe.presentation.home.navigation.main.MainNavGraph
import com.giraffe.presentation.home.navigation.main.routes.navigateToExplore
import javax.inject.Inject
import javax.inject.Provider

class HomeApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
    private val profileApi: Provider<ProfileApi>,
    private val matchApi: MatchApi
) : HomeApi {
    private lateinit var navController: NavHostController

    @Composable
    override fun MainContainer() {
        navController = rememberNavController()

        MainNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            profileApi = profileApi.get(),
            matchApi = matchApi
        )
    }

    override fun navigateToExploreScreen() {
        navController.navigateToExplore()
    }
}