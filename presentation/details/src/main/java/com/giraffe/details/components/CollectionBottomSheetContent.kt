package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun CollectionBottomSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(64.dp)
                .background(
                    Theme.color.brand.tertiary,
                    shape = RoundedCornerShape(Theme.radius.full)
                ),
            contentAlignment = Alignment.Center

        ) {
            Image(
                painter = painterResource(Theme.icons.dueTone.videoLibrary),
                contentDescription = stringResource(R.string.video_library_icon),
                colorFilter = ColorFilter.tint(Theme.color.brand.primary),
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = stringResource(R.string.no_collections_yet),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        Text(
            text = stringResource(R.string.create_a_new_collection_to_start_saving_your_favorite_movies_and_series),
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
        PrimaryButton(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.create_collection),
            enabled = true,
            isLoading = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CollectionBottomSheetContent()
}