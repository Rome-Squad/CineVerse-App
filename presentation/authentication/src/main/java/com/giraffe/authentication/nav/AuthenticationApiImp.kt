package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.authentication.AuthenticationApi

class AuthenticationApiImp (): AuthenticationApi {

    @Composable
    override fun LoginContainer(onBack: () -> Unit) {
        val navController = rememberNavController()

        AuthenticationNavGraph(
            navController = navController,
        )
    }
}