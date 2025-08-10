package com.giraffe.presentation.explore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import javax.inject.Inject

class ExploreApiImp @Inject constructor(
    private val detailsApi: DetailsApi
) : ExploreApi {
    @Composable
    override fun ExploreContainer(onShowBottomBarChange: (Boolean) -> Unit) {
        val navController: NavHostController = rememberNavController()

        ExploreNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}