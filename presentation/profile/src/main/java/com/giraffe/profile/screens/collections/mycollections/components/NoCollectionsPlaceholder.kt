package com.giraffe.profile.screens.collections.mycollections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.R

@Composable
fun NoCollectionsPlaceholder(
    modifier: Modifier = Modifier,
    onStartCollectingClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = 64.dp,
                    height = 80.dp
                )
                .padding(
                    bottom = 16.dp
                )
                .clip(CircleShape)
                .background(Theme.color.brand.tertiary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.videoLibrary),
                tint = Theme.color.shade.primary,
                contentDescription = stringResource(R.string.no_collections),
            )
        }

        Text(
            text = stringResource(R.string.no_collections_yet),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(
                    bottom = 8.dp
                )
        )


        Text(
            text = stringResource(R.string.start_building_your_personal_library_by_saving_movies_or_series_you_want_to_remember),
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(
                    bottom = 24.dp
                )
        )

        PrimaryButton(
            text = stringResource(R.string.start_collecting),
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.CenterHorizontally
                ),
            onClick = onStartCollectingClick
        )
    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewNoCollectionsPlaceholder() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        NoCollectionsPlaceholder(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewNoCollectionsPlaceholderDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        NoCollectionsPlaceholder(
            modifier = Modifier
        )
    }
}
