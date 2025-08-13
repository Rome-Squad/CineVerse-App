package com.giraffe.presentation.profile.screens.ratings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.BaseScreen
import com.giraffe.presentation.profile.screens.ratings.components.RatedItem
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.showToast
import com.giraffe.presentation.profile.utils.toStringResource

@Composable
fun RatingScreen(
    navigateToMovieDetails: (Int) -> Unit = {},
    navigateToSeriesDetails: (Int) -> Unit = {},
    navigateBack: () -> Unit = {},
    viewModel: RatingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            RatingEffect.NavigateBack -> navigateBack()
            is RatingEffect.NavigateToMovieDetails -> navigateToMovieDetails(effect.movieId)
            is RatingEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(effect.seriesId)
            is RatingEffect.ShowError -> context.showToast(effect.error.toStringResource())
        }
    }
    RatingContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun RatingContent(
    state: RatingScreenState,
    interaction: RatingInteractionListener
) {
    BaseScreen(
        title = stringResource(R.string.my_ratings),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick,
    ) {

        if (!state.isLoading && state.seriesPosters.isEmpty() && state.moviesPosters.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                MessageInfoBox(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(60.dp),
                    title = stringResource(R.string.no_rating_yet),
                    caption = stringResource(R.string.Rate_movies_and_series),
                    icon = painterResource(id = Theme.icons.dueTone.star),
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.button.disabled,
                    iconTintColor = Theme.color.brand.primary,
                    titlePrimaryButton = stringResource(R.string.start_rating),
                    isButtonsVisible = true,
                    isSecondaryButtonVisible = false,
                    onClickPrimaryButton = interaction::onStartRatingClick,
                )
            }
        } else {
            AnimatedVisibility(
                modifier = Modifier.padding(horizontal = 16.dp),
                visible = state.isTipVisible
            ) {
                InfoCard(
                    description = stringResource(R.string.tap_an_item_to_see_details_or_update_your_rating),
                    modifier = Modifier.fillMaxWidth(),
                    onClosedClick = interaction::onCloseTipClick
                )
            }
            Tabs(
                listOf(
                    stringResource(R.string.movies),
                    stringResource(R.string.series)
                ),
                selectedTabIndex = state.selectedTabIndex,
                onTabSelected = interaction::onTabSelected,
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.selectedPosters, key = { it.poster.id }) {
                    RatedItem(
                        ratedPoster = it,
                        onItemClick = interaction::onPosterClick,
                        onDeleteClick = interaction::onDeleteRatedPosterClick
                    )
                }
            }
        }
    }
}