package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.profile.ProfileApi
import javax.inject.Inject

class ProfileApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
) : ProfileApi {
    @Composable
    override fun ProfileContainer(onShowBottomBarChange: (Boolean) -> Unit) {

        val navController: NavHostController = rememberNavController()

        ProfileNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
        )
    }
}