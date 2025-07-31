package com.giraffe.match.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.match.MatchApi
import com.giraffe.match.R
import javax.inject.Inject

class MatchApiImp @Inject constructor(): MatchApi {

    @Composable
    override fun MatchContainer(onShowBottomBarChange: (Boolean) -> Unit) {
        MatchScreen(
            onShowBottomBarChange = onShowBottomBarChange
        )
    }

    @Composable
    fun MatchScreen(
        modifier: Modifier = Modifier,
        onShowBottomBarChange: (Boolean) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(
                    id = R.string.match_screen
                ),
            )
        }
    }
}