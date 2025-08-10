package com.giraffe.presentation.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.explore.ExploreApi
import com.giraffe.presentation.profile.screens.collections.collection.CollectionRoute
import com.giraffe.presentation.profile.screens.collections.mycollections.MyCollectionsRoute
import com.giraffe.presentation.profile.screens.settings.SettingsScreenRoute
import com.giraffe.api.explore.ExploreApi
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.profile.ProfileApi
import com.giraffe.profile.screens.collections.collection.CollectionRoute
import com.giraffe.profile.screens.collections.mycollections.MyCollectionsRoute
import com.giraffe.profile.screens.settings.SettingsScreenRoute
import javax.inject.Inject
import javax.inject.Provider

class ProfileApiImp @Inject constructor(
    private val authApiProvider: Provider<AuthenticationApi>,
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
) : ProfileApi {
    @Composable
    override fun CollectionContainer(
        collectionId: Int,
        collectionName: String,
        navigateBack: () -> Unit,
        onShowBottomBarChange: (Boolean) -> Unit,
    ) {
        val navController: NavHostController = rememberNavController()
        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = CollectionRoute(
                collectionId = collectionId,
                collectionName = collectionName
            ),
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            onShowBottomBarChange = onShowBottomBarChange,
            navigateBack = navigateBack
        )
    }

    @Composable
    override fun YourCollectionsContainer(
        onShowBottomBarChange: (Boolean) -> Unit,
        navigateBack: () -> Unit,
    ) {
        val navController: NavHostController = rememberNavController()
        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = MyCollectionsRoute,
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            onShowBottomBarChange = onShowBottomBarChange,
            navigateBack = navigateBack
        )
    }

    @Composable
    override fun ProfileContainer(
        onShowBottomBarChange: (Boolean) -> Unit,
    ) {

        val navController: NavHostController = rememberNavController()

        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = SettingsScreenRoute,
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}