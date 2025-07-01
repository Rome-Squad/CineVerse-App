package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    imageSource: Any?,
    shape: Shape = CircleShape,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = when (imageSource) {
        is String -> rememberAsyncImagePainter(model = imageSource) // URL
        is Int -> painterResource(id = imageSource) // drawable resource
        else -> throw IllegalArgumentException("Unsupported image source: $imageSource")
    }

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape),
        contentScale = contentScale
    )
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewImageCard() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        ImageCard(
            imageSource = null,
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewImageCardDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        ImageCard(
            imageSource = null,
            modifier = Modifier
        )
    }
}
