package com.giraffe.designsystem.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    caption: String = "",
    image: Painter = painterResource(Theme.icons.colored.logo),
    imageSize: Dp = 32.dp,
    endIcon: Painter = painterResource(Theme.icons.outline.add),
    hasBackground: Boolean = true,
    showBackButton: Boolean = false,
    showImage: Boolean = false,
    showTitle: Boolean = false,
    showCaption: Boolean = false,
    showEndIcon: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    onEndIconClick: () -> Unit = {},
) {
    val background = if (hasBackground) Theme.color.background.screen else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(background)
    ) {
        BackButton(
            showBackButton = showBackButton,
            onBackButtonClick = onBackButtonClick
        )
        DisplayImage(
            showImage = showImage,
            image = image,
            imageSize = imageSize
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Caption(showCaption, caption)
            Title(showTitle, title)
        }
        EndIcon(isVisible = showEndIcon, painter = endIcon, onEndIconClick = onEndIconClick)
    }
}

@Composable
private fun BackButton(showBackButton: Boolean, onBackButtonClick: () -> Unit) {
    if (showBackButton) {
        Icon(
            painter = painterResource(Theme.icons.outline.arrowLeft),
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    onClick = onBackButtonClick
                ),
        )
    }
}

@Composable
private fun DisplayImage(showImage: Boolean, image: Painter, imageSize: Dp) {
    if (showImage) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun Caption(showCaption: Boolean, caption: String) {
    if (showCaption) {
        Text(
            text = caption,
            maxLines = 1,
            style = Theme.textStyle.body.sm.regular,
            color = Theme.color.shade.secondary,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Title(showTitle: Boolean, title: String) {
    if (showTitle) {
        Text(
            text = title,
            maxLines = 1,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EndIcon(isVisible: Boolean, painter: Painter, onEndIconClick: () -> Unit) {
    if (isVisible) {
        Icon(
            painter = painter,
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable(
                    enabled = true,
                    onClick = onEndIconClick
                ),
        )
    }
}

@PreviewLightDark
@Composable
fun AppBarPreview() {
    CineVerseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AppBar(
                title = "Title",
                caption = "Caption",
                showBackButton = true,
                showImage = true,
                showTitle = true,
                showCaption = true,
                showEndIcon = true,
            )
            AppBar(
                title = "Title",
                showCaption = false,
                showBackButton = true,
                showImage = true,
                showTitle = true,
                showEndIcon = true,
            )
            AppBar(
                title = "Title",
                caption = "Caption",
                showBackButton = false,
                showEndIcon = false,
                showImage = true,
                showTitle = true,
                showCaption = true,
            )
            AppBar(
                title = "Title",
                showImage = false,
                showCaption = false,
                showEndIcon = false,
                showBackButton = true,
                showTitle = true,
            )
            AppBar(
                showImage = false,
                showTitle = false,
                showCaption = false,
                showEndIcon = false,
                showBackButton = true,
            )
            AppBar(
                title = "Title",
                showBackButton = false,
                showImage = false,
                showCaption = false,
                showEndIcon = false,
                showTitle = true,
            )
        }
    }
}