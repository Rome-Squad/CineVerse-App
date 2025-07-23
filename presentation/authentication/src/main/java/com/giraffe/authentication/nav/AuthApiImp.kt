package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.authentication.AuthApi

class AuthApiImp (
    private val authApi: AuthApi
): AuthApi {
    @Composable
    override fun WebViewContainer() {
        val navController = rememberNavController()

        AuthNavGraph(
            navController = navController,
            authApi = authApi
        )

    }

    @Composable
    override fun HomeContainer() {
        TODO("Not yet implemented")
    }
}