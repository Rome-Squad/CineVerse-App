package com.giraffe.details.components.gallery


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
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
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
    ) {
        Icon(
            painter = painterResource(Theme.icons.dueTone.image),
            contentDescription = imageUrl,
            tint = Theme.color.brand.secondary,
            modifier = modifier
                .background(
                    Theme.color.background.card,
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .wrapContentSize()
                .size(28.dp)
        )
    }
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