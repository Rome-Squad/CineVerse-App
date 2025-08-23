package com.giraffe.presentation.details.components.gallery


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.details.utils.toStrengthLevel
import com.giraffe.user.entity.ContentPreference

@Composable
fun GalleryItem(
    imageUrl: String?,
    contentPreference: ContentPreference,
    modifier: Modifier = Modifier
) {
    SafeIslamicImage(
        imageUrl = imageUrl.orEmpty(),
        contentDescription = imageUrl.orEmpty(),
        hasSensitiveText = false,
        placeholderTextStyle = Theme.textStyle.body.sm.medium.merge(color = Color(0xFFE1E1E3)),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg)),
        placeHolderTint = Theme.color.brand.secondary,
        placeholderModifier = Modifier
            .background(Theme.color.background.card)
            .fillMaxSize(),
        strengthLevel = contentPreference.toStrengthLevel()
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
                .width(96.dp),
            contentPreference = ContentPreference.HIDE_EXPLICIT
        )
    }
}