package com.giraffe.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.CTACard
import com.giraffe.designsystem.composable.CinePreview
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R

@Composable
fun AdvertisementSection(
    modifier: Modifier = Modifier,
    title: String,
    cardTitle: String,
    caption: String,
    onCardClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CTACard(
            title = cardTitle,
            caption = caption,
            icon = painterResource(Theme.icons.dueTone.magicStick),
            onClick = onCardClick
        )
    }
}

@CinePreview
@Composable
private fun Preview() {
    CineVerseTheme {
        AdvertisementSection(
            title = stringResource(R.string.what_should_i_watch),
            cardTitle = stringResource(R.string.let_us_choose_for_you),
            caption = stringResource(R.string.we_ll_help_you_skip_the_scroll_and_go_straight_to_the_good_stuff),
            onCardClick = {},
        )
    }
}