package com.giraffe.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface DetailsApi {
    fun NavGraphBuilder.castDetailsGraph(navController: NavHostController)
    fun NavGraphBuilder.castGalleryGraph(navController: NavHostController)
    fun NavGraphBuilder.castCreditsGraph(navController: NavHostController)
    fun NavGraphBuilder.castRecommendationGraph(navController: NavHostController)

    fun NavGraphBuilder.movieDetailsGraph(navController: NavHostController)
    fun NavGraphBuilder.movieRecommendationGraph(navController: NavHostController)

    fun NavGraphBuilder.movieCastsGraph(navController: NavHostController)
    fun NavGraphBuilder.movieReviewsCastsGraph(navController: NavHostController)

    fun NavGraphBuilder.seriesDetailsGraph(navController: NavHostController)
    fun NavGraphBuilder.seriesRecommendationGraph(navController: NavHostController)
    fun NavGraphBuilder.seriesCastsGraph(navController: NavHostController)
    fun NavGraphBuilder.seriesReviewsGraph(navController: NavHostController)
    fun NavGraphBuilder.seasonGraph(navController: NavHostController)
}