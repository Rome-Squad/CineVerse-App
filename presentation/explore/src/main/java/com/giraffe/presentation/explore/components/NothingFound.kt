package com.giraffe.presentation.explore.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R

@Composable
fun NothingFound(modifier: Modifier = Modifier) {
    MessageInfoBox(
        modifier = modifier,
        title = stringResource(R.string.nothing_found),
        caption = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else),
        icon = painterResource(Theme.icons.dueTone.search),
        buttonBackgroundColor = Theme.color.brand.primary,
        iconBackgroundColor = Theme.color.button.disabled,
        iconTintColor =Theme.color.brand.primary,
        isButtonsVisible = false
    )
}

@Preview(showBackground = false)
@Composable
fun NothingFoundPreview() {
    CineVerseTheme (isDarkTheme = true){
        NothingFound(modifier = Modifier.fillMaxSize())
    }
}