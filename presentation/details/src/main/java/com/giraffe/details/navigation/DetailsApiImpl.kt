package com.giraffe.details.navigation

import androidx.compose.runtime.Composable
import com.giraffe.details.DetailsApi
import kotlinx.serialization.Serializable

class DetailsApiImpl : DetailsApi {
    @Composable
    override fun GetSeriesDetailsContainer(seriesId: Int) {
//        val navController = rememberNavController()
//        NavHost(
//            navController = navController ,
//            startDestination = SeriesDetailsRoute,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            composable<SeriesDetailsRoute> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Series with id $seriesId", modifier = Modifier.clickable {
//                        navController.navigate(SeriesReviewsRoute)
//                    })
//                }
//            }
//            composable<SeriesReviewsRoute> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Reviews for series $seriesId", modifier = Modifier.clickable {
//                        navController.popBackStack(
//                            route = SeriesDetailsRoute,
//                            inclusive = true
//                        )
//                        navController.navigate(MovieDetailsRoute)
//                    })
//                }
//            }
//            composable<MovieDetailsRoute> {
//                GetMovieDetailsContainer(1022)
//            }
//        }
    }

    @Composable
    override fun GetMovieDetailsContainer(movieId: Int) {
//        val navController = rememberNavController()
//        NavHost(
//            navController = navController ,
//            startDestination = MovieDetailsRoute,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            composable<MovieDetailsRoute> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Movie with id $movieId", modifier = Modifier.clickable {
//                        navController.navigate(MovieReviews)
//                    })
//                }
//            }
//            composable<MovieReviews> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Reviews for movie $movieId", modifier = Modifier.clickable {
//                        navController.popBackStack(
//                            route = MovieDetailsRoute,
//                            inclusive = true
//                        )
//                        navController.navigate(SeriesDetailsRoute)
//                    })
//                }
//            }
//            composable<SeriesDetailsRoute> {
//                GetSeriesDetailsContainer(1022)
//            }
//        }
    }
}

@Serializable
data object MovieDetailsRoute

@Serializable
data object MovieReviews

@Serializable
data object SeriesDetailsRoute

@Serializable
data object SeriesReviewsRoute