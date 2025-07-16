package com.giraffe.details.components.gallery


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.islamicimageviewer.IslamicAppropriateImageViewer

@Composable
fun GalleryItem(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    IslamicAppropriateImageViewer(
        imageUrl = imageUrl.orEmpty(),
        placeHolderResId = Theme.icons.dueTone.image,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
    )
}


@Preview()
@Composable
fun GalleryItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GalleryItem(
            imageUrl = null,
            modifier = Modifier
                .height(128.dp)
                .width(96.dp)
        )
    }
}