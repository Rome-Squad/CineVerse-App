package com.giraffe.home.screen.show_more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.components.ListTitleSection
import com.giraffe.home.components.TransitionLazyColumnToGrid

@Composable
fun ShowMoreContent(
    state: ShowMoreUiState,
    showMoreInteractionListener: ShowMoreInteractionListener,
    onBackClick: () -> Unit,
    title: String,
) {
    Box {
        Column(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ListTitleSection(
                title = title,
                onBackClick = onBackClick
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransitionLazyColumnToGrid(
                    poster = state.mediaList,
                    isListSelected = state.isListSelected,
                    onClickItem = showMoreInteractionListener::onMediaClicked,
                )
            }

        }
        ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp),
            isListSelected = state.isListSelected,
            onGridSelected = showMoreInteractionListener::onViewChanged
        )
    }
}
