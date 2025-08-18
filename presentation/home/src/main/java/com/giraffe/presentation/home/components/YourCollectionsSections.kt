package com.giraffe.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.model.YourCollectionUi

@Composable
fun YourCollectionsSections(
    modifier: Modifier = Modifier,
    collectionItems: List<YourCollectionUi>,
    onShowMoreClick: () -> Unit,
    onCollectionClick: (collectionId: Int, collectionName: String) -> Unit,
    paddingHorizontal: Int = 16
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle(
            modifier = Modifier.padding(horizontal = paddingHorizontal.dp),
            title = stringResource(R.string.your_collections),
            clickableText = if (collectionItems.size >= 5) stringResource(R.string.show_more) else null,
            onClickableText = onShowMoreClick
        )

        val rows = if (collectionItems.size > 2) 2 else 1
        val gridHeight = if (rows == 2) 135.dp else 63.dp
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight),
            rows = GridCells.Fixed(rows),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(collectionItems) { item ->
                CollectionItem(
                    modifier = Modifier.height(63.dp),
                    text = item.title,
                    description = item.numberOfItems.toString(),
                    icon = Theme.icons.dueTone.folder,
                    onClick = {
                        onCollectionClick(item.id, item.title)
                    }
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun YourCollectionPreview() {
    val collectionItems = listOf(
        YourCollectionUi(
            id = 1,
            title = "hello",
            numberOfItems = 0
        ),
        YourCollectionUi(
            id = 1,
            title = "hello",
            numberOfItems = 0
        )
    )
    YourCollectionsSections(
        collectionItems = collectionItems,
        onShowMoreClick = {},
        onCollectionClick = { _, _ -> }
    )
}
