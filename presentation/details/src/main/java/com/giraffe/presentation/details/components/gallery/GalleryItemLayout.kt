package com.giraffe.presentation.details.components.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme

@Composable
fun GalleryItemLayoutLTR(
    imageUrls: List<String?>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(0.5f)
        ) {
            GalleryItem(
                imageUrl = imageUrls.getOrNull(1),
                modifier = Modifier
                    .background(
                        Theme.color.background.card,
                        shape = RoundedCornerShape(Theme.radius.lg)
                    )
                    .fillMaxHeight(0.475f)
                    .fillMaxWidth()
            )
            GalleryItem(
                imageUrl = imageUrls.getOrNull(2),
                modifier = Modifier
                    .background(
                        Theme.color.background.card,
                        shape = RoundedCornerShape(Theme.radius.lg)
                    )
                    .weight(1f)
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            )
        }
        GalleryItem(
            imageUrl = imageUrls.getOrNull(0),
            modifier = Modifier
                .background(
                    Theme.color.background.card,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
fun GalleryItemLayoutRTL(
    imageUrls: List<String?>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        GalleryItem(
            imageUrl = imageUrls.getOrNull(0),
            modifier = Modifier
                .background(
                    Theme.color.background.card,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .weight(1f)
                .fillMaxHeight()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(0.5f)

        ) {
            GalleryItem(
                imageUrl = imageUrls.getOrNull(1),
                modifier = Modifier
                    .background(
                        Theme.color.background.card,
                        shape = RoundedCornerShape(Theme.radius.lg)
                    )
                    .fillMaxHeight(0.475f)
                    .fillMaxWidth()
            )
            GalleryItem(
                imageUrl = imageUrls.getOrNull(2),
                modifier = Modifier
                    .background(
                        Theme.color.background.card,
                        shape = RoundedCornerShape(Theme.radius.lg)
                    )
                    .weight(1f)
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            )
        }
    }
}