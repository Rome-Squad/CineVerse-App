package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.profile.edit.editProfileWebViewRoute
import com.giraffe.profile.screens.profile.ProfileScreenRoute
import com.giraffe.profile.screens.profile.profileScreenRoute

@Composable
internal fun ProfileNavGraph(
    navController: NavHostController,
    onNavigateToLogin: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ProfileScreenRoute,
    ) {
        profileScreenRoute(
            navController = navController,
            onNavigateToLogin = onNavigateToLogin
        )
        editProfileWebViewRoute(navController)
    }
}