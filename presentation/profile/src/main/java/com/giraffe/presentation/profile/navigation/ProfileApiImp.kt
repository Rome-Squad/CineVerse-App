package com.giraffe.presentation.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.home.HomeApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.presentation.profile.navigation.routes.CollectionRoute
import com.giraffe.presentation.profile.navigation.routes.MyCollectionsRoute
import com.giraffe.presentation.profile.navigation.routes.SettingsScreenRoute
import javax.inject.Inject
import javax.inject.Provider

class ProfileApiImp @Inject constructor(
    private val authApiProvider: Provider<AuthenticationApi>,
    private val homeApi: HomeApi,
    private val detailsApi: DetailsApi,
) : ProfileApi {
    @Composable
    override fun CollectionContainer(
        collectionId: Int,
        collectionName: String,
        navigateBack: () -> Unit,
    ) {
        val navController: NavHostController = rememberNavController()
        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = CollectionRoute(
                collectionId = collectionId,
                collectionName = collectionName
            ),
            homeApi = homeApi,
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
            navigateBack = navigateBack
        )
    }

    @Composable
    override fun YourCollectionsContainer(
        navigateBack: () -> Unit,
    ) {
        val navController: NavHostController = rememberNavController()
        ProfileNavGraph(
            navController = navController,
            startDestinationRoute = MyCollectionsRoute,
            homeApi = homeApi,
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
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
            homeApi = homeApi,
            authenticationApi = authApiProvider.get(),
            detailsApi = detailsApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}