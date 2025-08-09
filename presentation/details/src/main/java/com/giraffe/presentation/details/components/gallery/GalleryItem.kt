package com.giraffe.presentation.details.components.gallery


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage

@Composable
fun GalleryItem(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    SafeIslamicImage(
        imageUrl = imageUrl.orEmpty(),
        contentDescription = imageUrl.orEmpty(),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg)),
        placeHolderTint = Theme.color.brand.secondary,
        placeholderModifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = Theme.color.stroke.primary,
                shape = RoundedCornerShape(
                    topStart = Theme.radius.lg,
                    bottomStart = Theme.radius.lg,
                    topEnd = Theme.radius.lg,
                    bottomEnd = Theme.radius.lg
                )
            )
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