package com.giraffe.details.screens.castCredit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.details.R
import com.giraffe.details.components.TransitionBetweenColumnAndVerticalGrid

@Composable
fun CastCreditContent(
    actorName: String,
    state: CastCreditScreenState,
    interaction: CastCreditInteractionListener,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column {
            AppBar(
                title = stringResource(R.string.best_of_, actorName),
                showBackButton = true,
                onBackButtonClick = onBackClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            TransitionBetweenColumnAndVerticalGrid(
                poster = state.posters,
                isListSelected = !state.isGridSelected,
                onPosterClicked = interaction::onPosterClick
            )
        }

        ViewToggle(
            isListSelected = !state.isGridSelected,
            onGridSelected = interaction::changeView,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp)
        )

    }

}