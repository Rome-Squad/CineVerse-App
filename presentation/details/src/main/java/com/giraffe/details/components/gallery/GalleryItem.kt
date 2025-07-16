package com.giraffe.details.components.gallery


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.imageviewer.islamicimageviewer.IslamicAppropriateImageViewer

@Composable
fun GalleryItem(
    image: String?,
    modifier: Modifier = Modifier
) {
    val model = ImageRequest
        .Builder(LocalContext.current)
        .data(image)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(model = model)
    val state by painter.state.collectAsState()


    if (state is AsyncImagePainter.State.Success) {
        IslamicAppropriateImageViewer(
            imageUrl = image.orEmpty(),
            placeHolderResId = Theme.icons.dueTone.image,
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
        )
    } else {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = Theme.color.brand.secondary,
                painter = painterResource(Theme.icons.dueTone.image),
                contentDescription = stringResource(R.string.galley_image)
            )
        }
    }
}


@Preview()
@Composable
fun GalleryItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GalleryItem(
            image = null,
            modifier = Modifier
                .height(128.dp)
                .width(96.dp)
        )
    }
}