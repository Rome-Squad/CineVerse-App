package com.giraffe.explore.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.uimodel.Poster

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransitionLazyColumnToGrid(
    poster: List<Poster>,
    isListSelected: Boolean = false,
) {
    SharedTransitionLayout {
        AnimatedContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            targetState = isListSelected,
            label = "ViewToggleAnimation"
        ) {
            if (it) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(items = poster) { poster ->
                        PosterHorizontal(
                            poster = poster,
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(items = poster) { poster ->
                        PosterVertically(
                            poster = poster,
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )
                    }
                }
            }
        }
    }
}