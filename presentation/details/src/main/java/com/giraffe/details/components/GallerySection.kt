package com.giraffe.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.giraffe.details.utils.imageSourceToPainter


@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    images: List<String?>,
    imageContentDescriptions: List<String?>,
    onShowMoreClick: () -> Unit,
) {
    if (images.isEmpty() || imageContentDescriptions.isEmpty()) {
        return
    }
    Column(
        modifier = modifier
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
                modifier = Modifier.clickable(
                    onClick = onShowMoreClick
                )
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
                modifier = Modifier.weight(0.5f)

            ) {
                GalleryItem(
                    image = images[0]?.imageSourceToPainter(),
                    contentDescription = imageContentDescriptions[0],
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                GalleryItem(
                    image = images[1]?.imageSourceToPainter(),
                    contentDescription = imageContentDescriptions[1],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
            }
            GalleryItem(
                image = images[2]?.imageSourceToPainter(),
                contentDescription = imageContentDescriptions[2],
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}


@Preview
@Composable
fun GallerySectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GallerySection(
            modifier = Modifier
                .height(314.dp)
                .fillMaxWidth(),
            images = listOf(
                null,
                null,
                "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg"
            ),
            imageContentDescriptions = listOf(
                "gallery_image_one",
                "gallery_image_two",
                "gallery_image_three"
            ),
            onShowMoreClick = {}
        )
    }
}