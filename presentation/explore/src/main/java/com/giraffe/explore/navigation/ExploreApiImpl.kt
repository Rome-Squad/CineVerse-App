package com.giraffe.explore.navigation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import kotlinx.serialization.Serializable

class ExploreApiImpl(
    private val detailsApi: DetailsApi
) : ExploreApi {

    @Composable
    override fun GetExploreContainer() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ExploreScreen
        ) {
            composable<ExploreScreen> {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = "explore",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                navController.popBackStack(
                                    route = ExploreScreen,
                                    inclusive = true
                                )
                                navController.navigate(
                                    route = MovieDetailsScreen
                                )
                            })
                }
            }

            composable<MovieDetailsScreen> {
                LaunchedEffect(Unit) {
                    Log.i("MovieDetailsScreen", detailsApi.hashCode().toString())
                }
                detailsApi.GetSeriesDetailsContainer(1022)

            }
        }
    }
}

@Serializable
data object ExploreScreen

@Serializable
data object MovieDetailsScreen