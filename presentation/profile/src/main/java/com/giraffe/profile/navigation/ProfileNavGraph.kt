
package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.profile.edit.editProfileWebViewRoute
import com.giraffe.profile.screens.profile.SettingsScreenRoute
import com.giraffe.profile.screens.profile.settingsScreenRoute
import androidx.compose.ui.Modifier

@Composable
internal fun ProfileNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToLogin: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SettingsScreenRoute,
    ) {
        settingsScreenRoute(
            navController = navController,
            onNavigateToLogin = onNavigateToLogin
        )
        editProfileWebViewRoute(navController)
    }

}
