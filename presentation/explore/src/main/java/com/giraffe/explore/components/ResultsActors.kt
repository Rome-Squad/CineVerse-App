package com.giraffe.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.explore.R

@Composable
fun ResultsActors() {
    var isListSelected by remember { mutableStateOf(false) }
    val castList = listOf(
        "Tom Hardy" to R.drawable.tom,
        "Emma Stone" to R.drawable.tom,
        "Chris Evans" to R.drawable.tom,
        "Zendaya" to R.drawable.tom,
        "Ryan Reynolds" to R.drawable.tom,
        "Scarlett Johansson" to R.drawable.tom,
        "Tom Hardy" to R.drawable.tom,
        "Emma Stone" to R.drawable.tom,
        "Chris Evans" to R.drawable.tom,
        "Zendaya" to R.drawable.tom,
        "Ryan Reynolds" to R.drawable.tom,
        "Scarlett Johansson" to R.drawable.tom,
        "Tom Hardy" to R.drawable.tom,
        "Emma Stone" to R.drawable.tom,
        "Chris Evans" to R.drawable.tom,
        "Zendaya" to R.drawable.tom,
        "Ryan Reynolds" to R.drawable.tom,
        "Scarlett Johansson" to R.drawable.tom,
        "Tom Hardy" to R.drawable.tom,
        "Emma Stone" to R.drawable.tom,
        "Chris Evans" to R.drawable.tom,
        "Zendaya" to R.drawable.tom,
        "Ryan Reynolds" to R.drawable.tom,
        "Scarlett Johansson" to R.drawable.tom,
    )

    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(castList) { (name, imageRes) ->
            CastItem(
                imagePainter = painterResource(id = imageRes),
                name = name,
            )
        }
    }

}

@Preview()
@Composable
private fun ResultsActorsPreview() {
    CineVerseTheme {
        ResultsActors()
    }
}