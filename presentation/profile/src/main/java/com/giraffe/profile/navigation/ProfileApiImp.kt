package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.profile.ProfileApi
import com.giraffe.profile.screens.collections.mycollections.MyCollectionsRoute
import com.giraffe.profile.screens.settings.SettingsScreenRoute
import javax.inject.Inject

class ProfileApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
) : ProfileApi {

    @Composable
    override fun MyCollectionsContainer(
        onShowBottomBarChange: (Boolean) -> Unit
    ) {
        val navController: NavHostController = rememberNavController()
        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = MyCollectionsRoute,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
    @Composable
    override fun ProfileContainer(
        onShowBottomBarChange: (Boolean) -> Unit
    ) {

        val navController: NavHostController = rememberNavController()

        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = SettingsScreenRoute,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}