package com.giraffe.details.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun GalleryItem(
    image: Painter?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    if (image == null) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = Theme.color.brand.secondary,
                painter = painterResource(Theme.icons.dueTone.image),
                contentDescription = contentDescription
            )
        }
    } else {
        Image(
            painter = image,
            contentDescription = stringResource(R.string.galley_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
        )
    }
}


@Preview()
@Composable
fun GalleryItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GalleryItem(
            contentDescription = null,
            image = null,
            modifier = Modifier
                .height(128.dp)
                .width(96.dp)
        )
    }
}