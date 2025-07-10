package com.giraffe.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.explore.ActorStateUi
import com.giraffe.explore.R

@Composable
fun ResultsActors(
    actorList: List<ActorStateUi>
) {
    var isListSelected by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(actorList) { media ->
            CastItem(
                imagePainter = painterResource(id = media.imageUrl.toInt()),
                name = media.name,
            )
        }
    }
}

@Preview()
@Composable
private fun ResultsActorsPreview() {
    val castList = listOf(
        ActorStateUi(
            id = 1,
            name = "Tom Hardy",
            imageUrl = painterResource(R.drawable.tom).toString()
        ),
        ActorStateUi(
            id = 1,
            name = "Tom Hardy",
            imageUrl = painterResource(R.drawable.tom).toString()
        ),
        ActorStateUi(
            id = 1,
            name = "Tom Hardy",
            imageUrl = painterResource(R.drawable.tom).toString()
        ),
        ActorStateUi(
            id = 1,
            name = "Tom Hardy",
            imageUrl = painterResource(R.drawable.tom).toString()
        ),
        ActorStateUi(
            id = 1,
            name = "Tom Hardy",
            imageUrl = painterResource(R.drawable.tom).toString()
        ),
    )

    CineVerseTheme {
        ResultsActors(castList)
    }
}