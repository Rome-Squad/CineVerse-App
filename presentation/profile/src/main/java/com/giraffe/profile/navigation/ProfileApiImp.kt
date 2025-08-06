package com.giraffe.profile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.profile.ProfileApi
import com.giraffe.profile.R
import javax.inject.Inject

class ProfileApiImp @Inject constructor(
    private val detailsApi: DetailsApi,
    private val exploreApi: ExploreApi,
    private val authenticationApi: AuthenticationApi
): ProfileApi {
    @Composable
    override fun ProfileContainer(onShowBottomBarChange: (Boolean) -> Unit) {

        val navController: NavHostController = rememberNavController()

        ProfileNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            exploreApi = exploreApi,
            authenticationApi = authenticationApi,
        )
    }
}