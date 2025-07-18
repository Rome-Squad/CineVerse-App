package com.giraffe.details.screens.castDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi

const val MovieDetailsRoute = "movie_details"
const val MovieReviewsRoute = "movie_reviews"
const val SeriesDetailsRoute = "series_details"
const val SeriesReviewsRoute = "series_reviews"


class DetailsApiImp : DetailsApi {

    @Composable
    override fun GetCastDetailsContainer(personId: Int) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun GetSeriesDetailsContainer(seriesId: Int) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SeriesDetailsRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(SeriesDetailsRoute) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Series with id $seriesId",
                        modifier = Modifier.clickable {
                            navController.navigate(SeriesReviewsRoute)
                        }
                    )
                }
            }

            composable(SeriesReviewsRoute) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "Reviews for series $seriesId")
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Back to Series Details")
                        }
                        Button(onClick = { /* handle movie nav */ }) {
                            Text("Go to Movie Details")
                        }
                    }
                }
            }
        }
    }

    @Composable
    override fun GetMovieDetailsContainer(movieId: Int) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MovieDetailsRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(MovieDetailsRoute) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Movie with id $movieId",
                        modifier = Modifier.clickable {
                            navController.navigate(MovieReviewsRoute)
                        }
                    )
                }
            }
            composable(MovieReviewsRoute) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "Reviews for movie $movieId")

                        Button(
                            onClick = { navController.popBackStack() }
                        ) {
                            Text("Back to Movie Details")
                        }

                        // Avoid circular navigation
                        Button(
                            onClick = {
                                // Handle series navigation at a higher level
                            }
                        ) {
                            Text("Go to Series Details")
                        }
                    }
                }
            }
        }
    }
}
