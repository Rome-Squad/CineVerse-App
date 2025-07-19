package com.giraffe.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.details.screens.castDetails.CastDetailsScreen
import com.giraffe.details.screens.gallery.GalleryScreen
import com.giraffe.details.screens.moviedetails.screen.MovieDetailsScreen
import com.giraffe.details.screens.recommended.RecommendedSeriesScreen
import com.giraffe.details.screens.reviewScreen.ReviewsScreen
import com.giraffe.details.screens.seasons.SeasonsScreen
import com.giraffe.details.screens.seriesdetails.SeriesDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class CastDetailsRoute(val personId: Int)

@Serializable
data class GallerRoute(val actorName: String, val imageUrls: List<String?>)

@Serializable
data class CreditsRoute(val personId: Int)

@Serializable
data class RecommendedCastRoute(val personId: Int, val title: String)

@Serializable
data class MovieDetailsRoute(val movieId: Int)

@Serializable
data class MoviesRecommendedRoute(val movieId: Int, val title: String)

@Serializable
data class MovieCastsRoute(val movieId: Int)

@Serializable
data class MovieReviewsRoute(val movieId: Int)

@Serializable
data class SeriesDetailsRoute(val seriesId: Int)

@Serializable
data class RecommendedSeriesRoute(val seriesId: Int, val title: String)

@Serializable
data class SeriesCastsRoute(val personId: Int)

@Serializable
data class SeriesReviewsRoute(val seriesId: Int)

@Serializable
data class SeasonsRoute(val seriesId: Int)


class DetailsApiImp : DetailsApi {
    override fun NavGraphBuilder.castDetailsGraph(navController: NavHostController) {
        composable<CastDetailsRoute> {
            val personId = it.toRoute<CastDetailsRoute>().personId
            CastDetailsScreen(personId, navController)
        }
    }

    override fun NavGraphBuilder.castGalleryGraph(navController: NavHostController) {
        composable<GallerRoute> {
            val args = it.toRoute<GallerRoute>()
            GalleryScreen(
                actorName = args.actorName,
                imageUrls = args.imageUrls,
                navController = navController,
            )
        }
    }

    override fun NavGraphBuilder.castCreditsGraph(navController: NavHostController) {
        composable<CreditsRoute> {

        }
    }

    override fun NavGraphBuilder.castRecommendationGraph(navController: NavHostController) {
        composable<RecommendedCastRoute> {
            val personId = it.toRoute<RecommendedCastRoute>().personId
            val personName = it.toRoute<RecommendedCastRoute>().title

        }
    }

    override fun NavGraphBuilder.movieDetailsGraph(navController: NavHostController) {
        composable<MovieDetailsRoute> {
            val movieId = it.toRoute<MovieDetailsRoute>().movieId
            MovieDetailsScreen(
                movieID = movieId,
                navController = navController,
                onBackButtonClick = {
                    navController.popBackStack()
                },
                navigateToReviews = {

                }
            )
        }
    }

    override fun NavGraphBuilder.movieRecommendationGraph(navController: NavHostController) {
        composable<MoviesRecommendedRoute> {
            val movieId = it.toRoute<MoviesRecommendedRoute>().movieId
            val movieName = it.toRoute<MoviesRecommendedRoute>().title

        }
    }

    override fun NavGraphBuilder.movieCastsGraph(navController: NavHostController) {
        composable<MovieCastsRoute> {
            val movieId = it.toRoute<MovieCastsRoute>().movieId

        }
    }

    override fun NavGraphBuilder.movieReviewsCastsGraph(navController: NavHostController) {
        composable<MovieReviewsRoute> {
            val movieId = it.toRoute<MovieReviewsRoute>().movieId

        }
    }

    override fun NavGraphBuilder.seriesDetailsGraph(navController: NavHostController) {
        composable<SeriesDetailsRoute> {
            val seriesId = it.toRoute<SeriesDetailsRoute>().seriesId
            SeriesDetailsScreen(
                seriesId = seriesId,
                navController = navController,
                navigateToReviews = {},
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }

    override fun NavGraphBuilder.seriesRecommendationGraph(navController: NavHostController) {
        composable<RecommendedSeriesRoute> {
            val seriesId = it.toRoute<RecommendedSeriesRoute>().seriesId
            val seriesName = it.toRoute<RecommendedSeriesRoute>().title

            RecommendedSeriesScreen(
                title = seriesName,
                seriesId = seriesId.toLong(),
                navController = navController,
            )
        }
    }

    override fun NavGraphBuilder.seriesCastsGraph(navController: NavHostController) {
        composable<SeriesCastsRoute> {
            val personId = it.toRoute<SeriesCastsRoute>().personId
            CastDetailsScreen(personId, navController)
        }
    }

    override fun NavGraphBuilder.seriesReviewsGraph(navController: NavHostController) {
        composable<SeriesReviewsRoute> {
            val seriesId = it.toRoute<SeriesReviewsRoute>().seriesId
        }
    }

    override fun NavGraphBuilder.seasonGraph(navController: NavHostController) {
        composable<SeasonsRoute> {
            val seriesId = it.toRoute<SeasonsRoute>().seriesId
            SeasonsScreen(seriesId, navController)
        }
    }
}