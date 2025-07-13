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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    endIcon: Painter = painterResource(Theme.icons.outline.add),
    hasBackground: Boolean = true,
    showBackButton: Boolean = true,
    showLogo: Boolean = true,
    showTitle: Boolean = true,
    showCaption: Boolean = true,
    showEndIcon: Boolean = true,
    onClickBack: () -> Unit = {}
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
            onClickBack = onClickBack
        )
        Logo(showLogo)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Caption(showCaption, caption)
            Title(showTitle, title)
        }
        EndIcon(showEndIcon, endIcon)

    }
}

@Composable
private fun BackButton(
    showBackButton: Boolean,
    onClickBack: () -> Unit = {}
) {
    if (showBackButton) {
        Icon(
            painter = painterResource(Theme.icons.outline.arrowLeft),
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClickBack),
        )
    }
}

@Composable
private fun Logo(showLogo: Boolean) {
    if (showLogo) {
        Image(
            painter = painterResource(Theme.icons.colored.logo),
            contentDescription = "",
            modifier = Modifier.size(32.dp)
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
private fun EndIcon(showEndIcon: Boolean, endIcon: Painter) {
    if (showEndIcon) {
        Icon(
            painter = endIcon,
            contentDescription = "",
            tint = Theme.color.shade.primary,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp),
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
                showBackButton = false,
                showEndIcon = false
            )
            AppBar(
                title = "Title",
                showEndIcon = false,
                showLogo = false,
                showCaption = false,
            )
            AppBar(
                showEndIcon = false,
                showLogo = false,
                showCaption = false,
                showTitle = false,
            )
            AppBar(
                title = "Title",
                showEndIcon = false,
                showLogo = false,
                showCaption = false,
                showBackButton = false,
            )
        }
    }
}