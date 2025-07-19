package com.giraffe.details.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.details.screens.castDetails.CastDetailsScreen
import com.giraffe.details.screens.gallery.GalleryScreen
import com.giraffe.details.screens.moviedetails.screen.MovieDetailsScreen
import com.giraffe.details.screens.seasons.SeasonsScreen
import com.giraffe.details.screens.recommended.RecommendedSeriesScreen
import com.giraffe.details.screens.seriesdetails.SeriesDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastDetailsRoute(val personId: Int)

@Serializable
data class GallerRoute(val actorName: String, val imageUrls: List<String?>)

@Serializable
data class CreditsRoute(val personId: Int)

@Serializable
data class MovieDetailsRoute(val movieId: Int)

@Serializable
data class MoviesRecommendedRoute(val movieId: Int)

@Serializable
data class MovieCastsRoute(val movieId: Int)

@Serializable
data class MovieReviewsRoute(val movieId: Int)

@Serializable
data class SeriesDetailsRoute(val seriesId: Int)

@Serializable
data class RecommendedSeriesRoute(val seriesId: Int, val title: String )

@Serializable
data class SeriesCastsRoute(val personId: Int)

@Serializable
data class SeriesReviewsRoute(val seriesId: Int)

@Serializable
data class SeasonsRoute(val seriesId: Int)


class DetailsApiImp : DetailsApi {

    @Composable
    override fun GetCastDetailsContainer(personId: Int) {
        DetailsNavHost(
            route = CastDetailsRoute(personId)
        ) {
            createCastDetailsScreen(it)
            createGalleryScreen(it)
            createCreditsScreen(it)
            createMovieDetailsScreen(it)
            createSeriesDetailsScreen(it)

        }
    }

    @Composable
    override fun GetMovieDetailsContainer(movieId: Int) {
        DetailsNavHost(
            route = MovieDetailsRoute(movieId)
        ) {
            createMovieDetailsScreen(it)
            createRecommendationMoviesScreen(it)
            createMovieCastsScreen(it)
            createMovieReviewsScreen(it)
            createCastDetailsScreen(it)
            createGalleryScreen(it)
            createCreditsScreen(it)
        }
    }

    @Composable
    override fun GetSeriesDetailsContainer(seriesId: Int) {
        DetailsNavHost(
            route = SeriesDetailsRoute(seriesId)
        ) {
            createSeriesDetailsScreen(it)
            createRecommendationSeriesScreen(it)
            createSeriesCastsScreen(it)
            createSeriesReviewsScreen(it)
            createSeasonScreen(it)
            createCastDetailsScreen(it)
            createGalleryScreen(it)
            createCreditsScreen(it)

        }
    }

    @Composable
    private fun DetailsNavHost(route: Any, builder: NavGraphBuilder.(NavHostController) -> Unit) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = route,
            builder = { builder(navController) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    private fun NavGraphBuilder.createCastDetailsScreen(navController: NavHostController) {
        composable<CastDetailsRoute> {
            val personId = it.toRoute<CastDetailsRoute>().personId
            CastDetailsScreen(personId, navController)
        }
    }

    private fun NavGraphBuilder.createGalleryScreen(navController: NavHostController) {
        composable<GallerRoute> {
            val args = it.toRoute<GallerRoute>()
            GalleryScreen(
                actorName = args.actorName,
                imageUrls = args.imageUrls,
                navController = navController,
            )
        }
    }

    private fun NavGraphBuilder.createCreditsScreen(navController: NavHostController) {
        composable<CreditsRoute> {
            val personId = it.toRoute<CreditsRoute>().personId

        }
    }

    private fun NavGraphBuilder.createMovieDetailsScreen(navController: NavHostController) {
        composable<MovieDetailsRoute> {
            val movieId = it.toRoute<MovieDetailsRoute>().movieId
            MovieDetailsScreen(movieId, navController)
        }
    }

    private fun NavGraphBuilder.createRecommendationMoviesScreen(navController: NavHostController) {
        composable<MoviesRecommendedRoute> {
            val movieId = it.toRoute<MoviesRecommendedRoute>().movieId

        }
    }

    private fun NavGraphBuilder.createMovieCastsScreen(navController: NavHostController) {
        composable<MovieCastsRoute> {
            val movieId = it.toRoute<MovieCastsRoute>().movieId

        }
    }

    private fun NavGraphBuilder.createMovieReviewsScreen(navController: NavHostController) {
        composable<MovieReviewsRoute> {
            val movieId = it.toRoute<MovieReviewsRoute>().movieId

        }
    }

    private fun NavGraphBuilder.createSeriesDetailsScreen(navController: NavHostController) {
        composable<SeriesDetailsRoute> {
            val seriesId = it.toRoute<SeriesDetailsRoute>().seriesId
            SeriesDetailsScreen(seriesId, navController)
        }
    }


    private fun NavGraphBuilder.createRecommendationSeriesScreen(navController: NavHostController) {
        composable<RecommendedSeriesRoute> {
            val seriesId = it.toRoute<RecommendedSeriesRoute>().seriesId
            val seriesName = it.toRoute<RecommendedSeriesRoute>().title


            RecommendedSeriesScreen(
                title = seriesName ,
                seriesId = seriesId.toLong(),
                navController = navController,
            )
        }
    }

    private fun NavGraphBuilder.createSeriesCastsScreen(navController: NavHostController) {
        composable<SeriesCastsRoute> {
            val personId = it.toRoute<SeriesCastsRoute>().personId
            CastDetailsScreen(personId,navController)
        }
    }

    private fun NavGraphBuilder.createSeriesReviewsScreen(navController: NavHostController) {
        composable<SeriesReviewsRoute> {
            val seriesId = it.toRoute<SeriesReviewsRoute>().seriesId

        }
    }

    private fun NavGraphBuilder.createSeasonScreen(navController: NavHostController) {
        composable<SeasonsRoute> {
            val seriesId = it.toRoute<SeasonsRoute>().seriesId
            SeasonsScreen(seriesId,navController)
        }
    }
}