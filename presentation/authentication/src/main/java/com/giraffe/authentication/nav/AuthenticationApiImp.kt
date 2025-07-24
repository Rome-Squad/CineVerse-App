package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.explore.ExploreApi

class AuthenticationApiImp (private val exploreApi: ExploreApi): AuthenticationApi {

    @Composable
    override fun LoginContainer(onBack: () -> Unit) {
        val navController = rememberNavController()

        AuthenticationNavGraph(
            navController = navController,
            exploreApi = exploreApi,
        )
    }
}