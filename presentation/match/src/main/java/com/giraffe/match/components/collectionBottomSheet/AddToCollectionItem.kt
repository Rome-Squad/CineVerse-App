package com.giraffe.match.components.collectionBottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.match.R

@Composable
fun AddToCollectionItem(
    title: String,
    isLoading: Boolean,
    onCollectionClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.bottomSheetCard)
            .clickable(onClick = onCollectionClicked)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = Theme.icons.dueTone.folder),
            contentDescription = stringResource(R.string.folder_icon),
            tint = Theme.color.brand.primary,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = title,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(visible = isLoading) {
            Progress(
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
@Preview()
fun PreviewAddToCollectionContentLight() {
    CineVerseTheme(isDarkTheme = false) {
        AddToCollectionItem(
            title = "My Folder",
            isLoading = true,
            modifier = Modifier
                .width(304.dp)
                .height(48.dp),
            onCollectionClicked = { }
        )
    }
}

@Composable
@Preview()
fun PreviewAddToCollectionContentDark() {
    CineVerseTheme(isDarkTheme = true) {
        AddToCollectionItem(
            title = "My Folder",
            isLoading = true,
            onCollectionClicked = {}
        )
    }
}