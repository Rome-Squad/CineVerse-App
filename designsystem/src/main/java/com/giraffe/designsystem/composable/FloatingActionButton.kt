package com.giraffe.designsystem.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.icon.outlineIcons
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) {

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(Theme.radius.lg),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.color.brand.primary,
            contentColor = Theme.color.button.onPrimary,
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun FloatingActionButtonPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        FloatingActionButton(
            modifier = Modifier
                .size(56.dp),
            icon = painterResource(id = outlineIcons.plus),
            contentDescription = ""
        ) {}
    }
}

@Preview
@Composable
fun FloatingActionButtonPreviewDark() {
    CineVerseTheme(
        isDarkTheme = true
    ) {
        FloatingActionButton(
            modifier = Modifier
                .size(56.dp),
            icon = painterResource(id = outlineIcons.plus),
            contentDescription = ""
        ) {}
    }
}