package com.giraffe.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun YourCollectionsSections(
    modifier: Modifier = Modifier,
    showMoreOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.your_collections),
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary
            )
            Text(
                modifier = Modifier.clickable { showMoreOnClick() },
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.brand.primary
            )
        }
        LazyHorizontalGrid(
            contentPadding = PaddingValues(16.dp),
            rows = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }
    }
}


@Preview
@Composable
fun YourCollectionsSectionsPreview() {
    CineVerseTheme(isDarkTheme = true) {
        YourCollectionsSections(modifier = Modifier.background(Theme.color.background.card))
    }
}