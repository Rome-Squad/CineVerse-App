package com.giraffe.match.navigation

import androidx.compose.runtime.Composable
import com.giraffe.match.MatchApi
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import javax.inject.Inject

class MatchApiImp @Inject constructor(
    private val detailsApi: DetailsApi
) : MatchApi {

    @Composable
    override fun MatchContainer(onShowBottomBarChange: (Boolean) -> Unit) {
        val navController = rememberNavController()

        MatchNavGraph(
            navController = navController,
            detailsApi = detailsApi
        )
    }
}

