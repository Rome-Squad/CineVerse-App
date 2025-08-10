package com.giraffe.match.navigation

import androidx.compose.runtime.Composable
import com.giraffe.match.MatchApi
import com.giraffe.match.screen.MatchScreen
import javax.inject.Inject

class MatchApiImp @Inject constructor(): MatchApi {

    @Composable
    override fun MatchContainer(onShowBottomBarChange: (Boolean) -> Unit) {
        MatchScreen(
            onShowBottomBarChange = onShowBottomBarChange
        )
    }
}