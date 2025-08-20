package com.giraffe.presentation.explore.screen.searchresult.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.presentation.explore.components.uimodel.Poster

@Composable
fun ActorsSection(
    modifier: Modifier = Modifier,
    actors: LazyPagingItems<Poster>,
    navigateToCastDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 99.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(actors.itemCount) { actorIndex ->
            CastItem(
                name = actors[actorIndex]?.name.toString(),
                imageUrl = actors[actorIndex]?.imageUrl.toString(),
                onClick = {
                    navigateToCastDetails(actors[actorIndex]?.id ?: 0)
                }
            )
        }
    }
}