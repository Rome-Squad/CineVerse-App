package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.details.DetailsApi
import com.giraffe.details.screens.castDetails.CastDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.MovieDetailsRoute
import com.giraffe.details.screens.seriesdetails.screen.SeriesDetailsRoute
import javax.inject.Inject
import javax.inject.Provider

class DetailsApiImp @Inject constructor(
    private val authApiProvider: Provider<AuthenticationApi>
) : DetailsApi {

    @Composable
    override fun MovieDetailsContainer(movieId: Int, onBackClick: () -> Unit) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = MovieDetailsRoute(movieId),
            onBackClick = onBackClick,
            authApi = authApiProvider.get(),
        )
    }

    @Composable
    override fun SeriesDetailsContainer(seriesId: Int, onBackClick: () -> Unit) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = SeriesDetailsRoute(seriesId),
            onBackClick = onBackClick,
            authApi = authApiProvider.get(),
        )
    }

    @Composable
    override fun CastDetailsContainer(castId: Int, onBackClick: () -> Unit) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = CastDetailsRoute(castId),
            onBackClick = onBackClick,
            authApi = authApiProvider.get(),
        )
    }
}