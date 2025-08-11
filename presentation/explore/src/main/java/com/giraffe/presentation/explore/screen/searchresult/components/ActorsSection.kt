package com.giraffe.presentation.explore.screen.searchresult.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.designsystem.uimodel.Poster

@Composable
fun ActorsSection(
    modifier: Modifier = Modifier,
    actors: LazyPagingItems<Poster>,
    navigateToCastDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(actors.itemCount) { actorIndex ->
            CastItem(
                name = actors[actorIndex]?.name.toString(),
                imageUrl = actors[actorIndex]?.imageUri.toString(),
                onClick = {
                    navigateToCastDetails(actors[actorIndex]?.id ?: 0)
                }
            )
        }
    }
}