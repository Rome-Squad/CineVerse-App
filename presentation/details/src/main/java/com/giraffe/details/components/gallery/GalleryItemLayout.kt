package com.giraffe.details.components.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GalleryItemLayoutLTR(
    images: List<String?>,
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
            images.getOrNull(1)?.let {
                GalleryItem(
                    image = it,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            }
            images.getOrNull(2)?.let {
                GalleryItem(
                    image = it,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            }
        }
        images.getOrNull(0)?.let {
            GalleryItem(
                image = it,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun GalleryItemLayoutRTL(
    images: List<String?>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        images.getOrNull(0)?.let {
            GalleryItem(
                image = it,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(0.5f)

        ) {
            images.getOrNull(1)?.let {
                GalleryItem(
                    image = it,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            }
            images.getOrNull(2)?.let {
                GalleryItem(
                    image = it,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            }
        }
    }
}