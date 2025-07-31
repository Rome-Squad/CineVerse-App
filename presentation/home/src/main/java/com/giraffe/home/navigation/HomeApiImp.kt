package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.home.HomeApi
import javax.inject.Inject

class HomeApiImp @Inject constructor(
    private val detailsApi: DetailsApi
) : HomeApi {
    @Composable
    override fun HomeContainer(onShowBottomBarChange: (Boolean) -> Unit) {
        val navController: NavHostController = rememberNavController()
        HomeNavGraph(
            navController = navController,
            detailsApi = detailsApi,
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}