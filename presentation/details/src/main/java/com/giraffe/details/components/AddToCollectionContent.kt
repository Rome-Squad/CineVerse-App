package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun AddToCollectionContent(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.bottomSheetCard),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = Theme.icons.dueTone.folder),
                contentDescription = stringResource(R.string.folder_icon),
                tint = Theme.color.brand.primary,
                modifier = Modifier.padding(end = 8.dp).size(20.dp)
            )
            Text(
                text = title,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.primary
            )
        }

        if (isLoading) {
            Progress(
                modifier = Modifier.padding(end = 12.dp),
            )
        }
    }
}

@Composable
@Preview(
    name = "AddToCollection Light",
    showBackground = false,
    showSystemUi = false
)
fun PreviewAddToCollectionContentLight() {
    CineVerseTheme(isDarkTheme = false) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true
        )
    }
}

@Composable
@Preview(
    name = "AddToCollection Dark",
    showBackground = false,
    showSystemUi = false
)
fun PreviewAddToCollectionContentDark() {
    CineVerseTheme(isDarkTheme = true) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true
        )
    }
}