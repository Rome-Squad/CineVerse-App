package com.giraffe.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R
import com.giraffe.home.screen.home.YourCollectionUiModel

@Composable
fun YourCollectionsSections(
    modifier: Modifier = Modifier,
    collectionItems: List<YourCollectionUiModel>,
    onShowMoreClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.your_collections),
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary
            )
            Text(
                modifier = Modifier.noHoverClickable(onClick = onShowMoreClick),
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.brand.primary
            )
        }
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp),
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(collectionItems) { item ->
                CollectionItem(
                    modifier = Modifier.height(63.dp),
                    text = item.title,
                    description = item.numberOfItems.toString(),
                    icon = Theme.icons.dueTone.folder
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun YourCollectionPreview() {
    val collectionItems = listOf(
        YourCollectionUiModel(
            id = 1,
            title = "hello",
            numberOfItems = 0
        ),
        YourCollectionUiModel(
            id = 1,
            title = "hello",
            numberOfItems = 0
        )
    )
    YourCollectionsSections(
        collectionItems = collectionItems,
        onShowMoreClick = {}
    )
}
