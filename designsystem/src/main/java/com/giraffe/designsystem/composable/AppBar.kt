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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    caption: String? = null,
    image: Painter? = null,
    imageSize: Dp = 32.dp,
    endIcon: Painter? = null,
    hasBackground: Boolean = true,
    showBackButton: Boolean = false,
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
            image = image,
            imageSize = imageSize,
            modifier = Modifier
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Caption(
                caption=caption,
                modifier= Modifier
            )
            Title(
                title = title,
                modifier= Modifier
            )
        }
        EndIcon(painter = endIcon, onEndIconClick = onEndIconClick)
    }
}

@Composable
private fun BackButton(showBackButton: Boolean, onBackButtonClick: () -> Unit) {
    if (showBackButton) {
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        val rotationAngle = if (isRtl) 180f else 0f
        Icon(
            painter = painterResource(Theme.icons.outline.arrowLeft),
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable(
                    onClick = onBackButtonClick
                ).graphicsLayer(rotationZ = rotationAngle)
        )
    }
}

@Composable
private fun DisplayImage(image: Painter?, imageSize: Dp,modifier: Modifier= Modifier) {
    image?.let {
        Image(
            painter = it,
            contentDescription = "",
            modifier = modifier
                .size(imageSize)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun Caption(caption: String?,modifier: Modifier = Modifier) {
    caption?.let {
        Text(
            text = it,
            maxLines = 1,
            style = Theme.textStyle.body.sm.regular,
            color = Theme.color.shade.secondary,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
        )
    }
}

@Composable
private fun Title(title: String?,modifier: Modifier) {
    title?.let {
        Text(
            text = it,
            maxLines = 1,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
        )
    }
}

@Composable
private fun EndIcon(painter: Painter?, onEndIconClick: () -> Unit,modifier: Modifier = Modifier) {
    painter?.let {
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        val rotationAngle = if (isRtl) 180f else 0f
        Icon(
            painter = it,
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable(
                    enabled = true,
                    onClick = onEndIconClick
                ).graphicsLayer(rotationZ = rotationAngle)
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
                image = painterResource(Theme.icons.colored.logo),
                endIcon = painterResource(Theme.icons.outline.add),
                showBackButton = true,
            )
            AppBar(
                title = "Title",
                showBackButton = true,
                image = painterResource(Theme.icons.colored.logo),
                endIcon = painterResource(Theme.icons.outline.add),
            )
            AppBar(
                title = "Title",
                caption = "Caption",
                image = painterResource(Theme.icons.colored.logo),
                showBackButton = false,
            )
            AppBar(
                title = "Title",
                showBackButton = true,
            )
            AppBar(
                showBackButton = true,
            )
            AppBar(
                title = "Title",
                showBackButton = false,
            )
        }
    }
}