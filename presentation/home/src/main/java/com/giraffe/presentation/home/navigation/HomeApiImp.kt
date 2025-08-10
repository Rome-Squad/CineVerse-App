package com.giraffe.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.home.HomeApi
import com.giraffe.match.MatchApi
import com.giraffe.presentation.home.navigation.main.MainNavGraph
import com.giraffe.profile.ProfileApi
import javax.inject.Inject

class HomeApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
    private val profileApi: ProfileApi,
    private val matchApi: MatchApi
) : HomeApi {
    @Composable
    override fun MainContainer() {
        val navController: NavHostController = rememberNavController()
        MainNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            profileApi = profileApi,
            matchApi = matchApi
        )
    }
}