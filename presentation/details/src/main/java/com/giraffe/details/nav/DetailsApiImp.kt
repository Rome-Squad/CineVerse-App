package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi

class DetailsApiImp : DetailsApi {

    @Composable
    override fun MovieDetailsContainer(movieId: Int) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = MovieDetailsRoute(movieId),
        )
    }

    @Composable
    override fun SeriesDetailsContainer(seriesId: Int) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = SeriesDetailsRoute(seriesId),
        )
    }

    @Composable
    override fun CastDetailsContainer(castId: Int) {
        val navController: NavHostController = rememberNavController()

        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = CastDetailsRoute(castId)
        )
    }
}