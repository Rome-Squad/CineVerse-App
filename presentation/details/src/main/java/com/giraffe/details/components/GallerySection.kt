package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R


@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    images: List<Pair<Int?, String?>>,
) {
    Column(
        modifier = modifier
            .background(Theme.color.background.screen)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.gallery),
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary
            )
            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.brand.primary,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                GalleryItem(
                    image = images[0].first,
                    contentDescription = images[0].second,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                GalleryItem(
                    image = images[1].first,
                    contentDescription = images[1].second,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
            GalleryItem(
                image = images[2].first,
                contentDescription = images[2].second,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GallerySectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GallerySection(
            modifier = Modifier.height(314.dp),
            images = listOf(
                Pair(null, "gallery_image_one"),
                Pair(null, "gallery_image_two"),
                Pair(R.drawable.gallery_item3, "gallery_image_three"),
            )
        )
    }
}