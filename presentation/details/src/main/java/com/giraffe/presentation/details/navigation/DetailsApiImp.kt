package com.giraffe.presentation.details.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.presentation.details.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.details.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.details.navigation.routes.SeriesDetailsRoute
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