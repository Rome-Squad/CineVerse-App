package com.giraffe.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R

@Composable
fun CollectionListSection(
    modifier: Modifier = Modifier,
    collectionItems: List<CollectionItemData>
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
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(collectionItems.chunked(2)) { rowItems ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { item ->
                        CollectionItem(
                            modifier = Modifier.width(280.dp),
                            collectionItemData = item
                        )
                    }
                }
            }
        }
    }
}