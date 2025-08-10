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
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.screen.home.FeaturedCollectionUiModel

@Composable
fun CollectionListSection(
    modifier: Modifier = Modifier,
    collectionItems: List<FeaturedCollectionUiModel>,
    onCollectionItemClick: (Int, String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.featured_collections),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary
        )

        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(collectionItems) { item ->
                CollectionItem(
                    collectionItemData = item,
                    onClick = { onCollectionItemClick(item.id, item.title) }
                )
            }
        }
    }
}
