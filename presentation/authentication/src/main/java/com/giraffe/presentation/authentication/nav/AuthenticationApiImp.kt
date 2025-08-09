package com.giraffe.presentation.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.home.HomeApi
import com.giraffe.api.authentication.AuthenticationApi
import javax.inject.Inject

class AuthenticationApiImp @Inject constructor(
    private val homeApi: HomeApi
) : AuthenticationApi {

    @Composable
    override fun LoginContainer(
        onBack: () -> Unit,
        isOnboardingFirstTime: Boolean,
        isLoggedIn: Boolean
    ) {
        val navController = rememberNavController()

        AuthenticationNavGraph(
            navController = navController,
            homeApi = homeApi,
            isOnboardingFirstTime = isOnboardingFirstTime,
            isLoggedIn = isLoggedIn,
        )
    }
}