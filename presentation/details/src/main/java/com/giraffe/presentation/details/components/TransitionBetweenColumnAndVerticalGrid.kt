package com.giraffe.presentation.details.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.user.entity.ContentPreference

@Composable
fun TransitionBetweenColumnAndVerticalGrid(
    posters: List<Poster>,
    isListSelected: Boolean = false,
    contentPreference: ContentPreference,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onPosterClicked: (Int, String) -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        state = gridState,
        columns = GridCells.Adaptive(minSize = if (isListSelected) 328.dp else 156.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        itemsIndexed(
            items = posters,
            key = { index, poster -> "${poster.id} + ${poster.date} + ${poster.time} + $index" }
        ) { _, poster ->
            MediaPoster(
                poster = poster,
                isGridSelected = !isListSelected,
                onClick = {
                    onPosterClicked(poster.id, poster.mediaTypeOfPoster.toString())
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .animateItem(
                        fadeInSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                        placementSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                        fadeOutSpec = spring(dampingRatio = 0.85f, stiffness = 100f)
                    ),
                contentPreference = contentPreference
            )
        }
    }
}