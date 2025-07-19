package com.giraffe.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.uimodel.Poster

@Composable
fun ResultsActors(
    actorList: List<Poster>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(actorList) { media ->
            CastItem(
                imageUrl = media.imageUri,
                name = media.name,
                onClick = {}
            )
        }
    }
}

