package com.giraffe.presentation.details.navigation.api

import android.content.Context
import android.content.Intent
import com.giraffe.api.details.DetailsApi
import com.giraffe.presentation.details.navigation.main.DetailsActivity
import com.giraffe.presentation.details.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.details.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.details.navigation.routes.SeriesDetailsRoute
import com.giraffe.presentation.details.navigation.routes.StartDestination
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DetailsApiImp @Inject constructor() : DetailsApi {

    override fun launchMovieDetails(context: Context, movieId: Int) {
        val startDestination: StartDestination = MovieDetailsRoute(movieId)
        launchDetailsActivity(context, startDestination)
    }

    override fun launchSeriesDetails(context: Context, seriesId: Int) {
        val startDestination: StartDestination = SeriesDetailsRoute(seriesId)
        launchDetailsActivity(context, startDestination)
    }

    override fun launchCastDetails(context: Context, castId: Int) {
        val startDestination: StartDestination = CastDetailsRoute(castId)
        launchDetailsActivity(context, startDestination)
    }

    private fun launchDetailsActivity(context: Context, startDestination: StartDestination) {
        val intent = Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.START_DESTINATION, Json.encodeToString(startDestination))
        }
        context.startActivity(intent)
    }
}