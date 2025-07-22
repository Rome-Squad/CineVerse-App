package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.gallery.galleryRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.navigateToReviews
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

@Composable
internal fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: Any,
    onBackClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            navigateToReviews = navController::navigateToReviews,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
        )

        seriesDetailsRoute(
            navController = navController,
            navigateToReviews = navController::navigateToReviews,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
        )

        castDetailsRoute(
            navController = navController,
            onBackButtonClick = { if (navController.popBackStack().not()) onBackClick() },
        )

        galleryRoute(navController)

//        reviewRoute(navController)
    }
}
