package com.giraffe.presentation.profile.screens.collectiondetails

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.DeleteButton
import com.giraffe.presentation.profile.components.ScreenStates
import com.giraffe.presentation.profile.components.SwipableItem
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.toStringResource

@Composable
fun CollectionScreen(
    navigateBack: () -> Unit = {},
    navigateToMovieDetails: (Int) -> Unit = {},
    viewModel: CollectionDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            CollectionDetailsEffect.NavigateBack -> navigateBack()
            is CollectionDetailsEffect.NavigateToMovieDetails -> navigateToMovieDetails(effect.movieId)
            is CollectionDetailsEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.error.toStringResource()),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    CollectionScreenContent(
        state = state,
        interactions = viewModel
    )
}

@Composable
private fun CollectionScreenContent(
    state: CollectionDetailsScreenState,
    interactions: CollectionDetailsInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = state.collectionName,
            showBackButton = true,
            onBackButtonClick = interactions::onBackClick
        )
        ScreenStates(
            isLoading = state.isLoading,
            isNoInternet = state.isNoInternet
        ) {
            LazyColumn(
                modifier = Modifier
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
    }
}