package com.giraffe.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R

@Composable
fun CollectionListSection(
    modifier: Modifier = Modifier,
    collectionItems: List<Pair<String, Int>>
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.featured_collections),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary
        )

        LazyHorizontalGrid(
            contentPadding = PaddingValues(horizontal = 16.dp),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = collectionItems.size,
            ) {
                CollectionItem(
                    modifier = Modifier
                        .width(280.dp)
                        .height(92.dp),
                    image = collectionItems[it].second,
                    collectionType = collectionItems[it].first
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CollectionListSectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        CollectionListSection(
            modifier = Modifier
                .background(Theme.color.shade.quaternary)
                .height(250.dp),
            collectionItems =
                listOf(
                    "Action" to R.drawable.collection_image,
                    "Action" to R.drawable.collection_image,
                    "Action" to R.drawable.collection_image,
                    "Comedy" to R.drawable.collection_image,
                    "Action" to R.drawable.collection_image,
                    "Comedy" to R.drawable.collection_image,
                )
        )
    }
}