package com.giraffe.explore.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SafeIslamicImage(
            imageUrl = imageUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            hasSensitiveText = false,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .clickable(onClick = onClick),
            placeHolderTint = Theme.color.brand.secondary,
            placeholderModifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke.primary,
                    RoundedCornerShape(Theme.radius.lg)
                )
        )

        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121321)
@Composable
fun CastItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        Box(Modifier.width(80.dp)) {
            CastItem(
                imageUrl = "",
                name = "Tom Hardy",
                onClick = {}
            )
        }

    }
}