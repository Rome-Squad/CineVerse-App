package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.HomeApi
import com.giraffe.match.MatchApi
import com.giraffe.profile.ProfileApi
import javax.inject.Inject

class HomeApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
    private val profileApi: ProfileApi,
    private val matchApi: MatchApi
) : HomeApi {
    @Composable
    override fun HomeContainer() {
        val navController: NavHostController = rememberNavController()
        HomeNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            profileApi = profileApi,
            matchApi = matchApi
        )
    }
}