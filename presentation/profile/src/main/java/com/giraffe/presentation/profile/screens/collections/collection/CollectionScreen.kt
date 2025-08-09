package com.giraffe.presentation.profile.screens.collections.collection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.DeleteButton
import com.giraffe.presentation.profile.components.SwipableItem
import com.giraffe.presentation.profile.utils.EffectListener

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToMovieDetails: (Int) -> Unit = {},
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val isNoInternet by viewModel.isNoInternet.collectAsState()
    val interactions: CollectionInteractionListener = viewModel

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            CollectionEffect.NavigateBack -> {
                navigateBack()
            }

            is CollectionEffect.NavigateToMovieDetails -> {
                navigateToMovieDetails(effect.movieId)
            }

            is CollectionEffect.ShowError -> {
                //TODO()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = state.value.collectionName,
            showBackButton = true,
            onBackButtonClick = interactions::onBackClick
        )

        if (isNoInternet) {
            NoInternetScreen()
        }

        if (!isNoInternet) {
            CollectionScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                interactions = viewModel
            )
        }

    }


}

@Composable
fun CollectionScreenContent(
    modifier: Modifier = Modifier,
    state: CollectionScreenState,
    interactions: CollectionInteractionListener
) {
    LazyColumn(
        modifier = modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {

            AnimatedVisibility(
                visible = state.isDeleteTipVisible
            ) {
                InfoCard(
                    description = stringResource(
                        id = R.string.tip_swipe_left_to_remove_movies_from_your_collection
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClosedClick = interactions::onCloseTipClick
                )

            }
        }


        items(state.collectionMovies) { swipeablePoster ->
            SwipableItem(
                actionButton = {
                    DeleteButton(
                        onDeleteClick = {
                            interactions.onDeletePosterClick(swipeablePoster.poster.id)
                        }
                    )
                },
            ) {
                PosterItemHorizontal(
                    modifier = Modifier.fillMaxWidth(),
                    movie = swipeablePoster.poster,
                    onClickPoster = {
                        interactions.onPosterClick(swipeablePoster.poster.id)
                    }
                )
            }
        }
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewCollectionScreen() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        CollectionScreen(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewCollectionScreenDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        CollectionScreen(
            modifier = Modifier
        )
    }
}
