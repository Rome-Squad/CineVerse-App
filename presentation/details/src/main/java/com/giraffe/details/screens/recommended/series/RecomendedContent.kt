package com.giraffe.details.screens.recommended.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.recommended.TransitionLazyColumnToGridSeries
import com.giraffe.details.models.SeriesUi

@Composable
fun RecommendedContent(
    title: String,
    navController: NavController,
    lazyPagingItems: LazyPagingItems<SeriesUi>,
    modifier: Modifier = Modifier,
) {
    var isGridSelected by rememberSaveable { mutableStateOf(false) }

    Box {
        LazyColumn(
            modifier = modifier
                .background(Theme.color.background.screen)
                .statusBarsPadding()
                .systemBarsPadding(),
        ) {
            item {
                AppBar(
                    title = title,
                    caption = stringResource(R.string.because_you_watched),
                    showBackButton = true,
                    modifier = Modifier.padding(16.dp),
                    onBackButtonClick = {navController.popBackStack()},
                )
            }

            item {
                CardsSection(
                    modifier = Modifier.fillParentMaxHeight(),
                    lazyPagingItems = lazyPagingItems,
                    isGridSelected = !isGridSelected
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
    lazyPagingItems: LazyPagingItems<SeriesUi>,
    isGridSelected: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGridSeries(
            lazyPagingItems = lazyPagingItems,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 5.dp),
        )
    }
}

