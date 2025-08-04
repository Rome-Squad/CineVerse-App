package com.giraffe.onboarding.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.onboarding.OnBoardingApi
import jakarta.inject.Inject

class OnBoardingApiImp @Inject constructor(
    private val authenticationApi: AuthenticationApi,
) : OnBoardingApi {

    @Composable
    override fun OnBoardingContainer() {
        val navController = rememberNavController()
        OnBoardingNavGraph(
            navController = navController,
            authenticationApi = authenticationApi
        )
    }
}