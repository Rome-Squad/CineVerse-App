package com.giraffe.details.screens.recommended.movie

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.models.MovieUi

@Composable
fun RecommendedContent(
    title: String,
    lazyPagingItems: LazyPagingItems<MovieUi>,
    interaction: RecommendedInteractionListener,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isGridSelected by rememberSaveable { mutableStateOf(false) }

    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.color.background.screen)
                .systemBarsPadding()
        ) {
            item {
                AppBar(
                    title = title,
                    caption = stringResource(R.string.because_you_watched),
                    showBackButton = true,
                    modifier = Modifier.padding(8.dp),
                    onBackButtonClick = onBackClick
                )
            }

            item {
                CardsSection(
                    modifier = Modifier.fillParentMaxHeight(),
                    lazyPagingItems = lazyPagingItems,
                    isGridSelected = !isGridSelected,
                    onItemClick = interaction::navigateToMovieDetailsScreen
                )
            }
        }


        ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp),
            isListSelected = isGridSelected,
            onGridSelected = { isGridSelected = !it }
        )
    }
}


@Composable
private fun CardsSection(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<MovieUi>,
    isGridSelected: Boolean,
    onItemClick: (Int) -> Unit,

    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGridMovie(
            lazyPagingItems = lazyPagingItems,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 5.dp),
            onItemClick = { movieId ->
                movieId?.let {
                    onItemClick(it)
                } ?: Log.e("RecommendedMovies", "Movie ID is null.")}
        )
    }
}

