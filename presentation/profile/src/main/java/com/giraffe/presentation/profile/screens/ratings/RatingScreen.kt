package com.giraffe.presentation.profile.screens.ratings

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.DeleteButton
import com.giraffe.presentation.profile.components.SwipableItem
import com.giraffe.presentation.profile.model.RatedPoster
import com.giraffe.presentation.profile.utils.EffectListener
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

            is RatingEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.error.toStringResource()),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    RatingContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun RatingContent(
    modifier: Modifier = Modifier,
    state: RatingScreenState,
    interaction: RatingInteractionListener
) {
    Column(
        modifier = modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = stringResource(R.string.my_ratings),
            showBackButton = true,
            onBackButtonClick = interaction::onBackClick
        )
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

@Composable
private fun RatedItem(
    modifier: Modifier = Modifier,
    ratedPoster: RatedPoster,
    onItemClick: (RatedPoster) -> Unit = {},
    onDeleteClick: (RatedPoster) -> Unit = {}
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RateSection(
            modifier = Modifier.fillMaxWidth(),
            rate = ratedPoster.rating.toDouble(),
        )
        SwipableItem(
            actionButton = {
                DeleteButton(
                    onDeleteClick = { onDeleteClick(ratedPoster) }
                )
            },
        ) {
            PosterItemHorizontal(
                modifier = modifier.fillMaxWidth(),
                movie = ratedPoster.poster,
                onClickPoster = { onItemClick(ratedPoster) }
            )
        }
    }
}

@Composable
private fun RateSection(modifier: Modifier = Modifier, rate: Double) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "You gave it",
                style = Theme.textStyle.body.sm.medium,
                color = Theme.color.shade.primary
            )
            Stars(rate = rate)
        }
    }
}

@Composable
private fun Stars(modifier: Modifier = Modifier, rate: Double) {
    var remainRate = rememberSaveable { rate.toInt() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            val iconRes = if (remainRate > 0) Theme.icons.bold.star else Theme.icons.outline.star
            val iconTint =
                if (remainRate > 0) Theme.color.additional.primary.yellow else Theme.color.shade.tertiary
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(iconRes),
                contentDescription = "star",
                tint = iconTint,
            )
            remainRate--
        }
    }
}