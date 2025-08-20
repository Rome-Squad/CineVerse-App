package com.giraffe.match.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.screen.match_pager.SelectionOption
import com.giraffe.presentation.match.R

@Composable
fun getMoodOptions(): List<SelectionOption> {
    return listOf(
        SelectionOption(1, stringResource(id = R.string.mood_chill), icon = painterResource(Theme.icons.dueTone.headphone)),
        SelectionOption(2, stringResource(id = R.string.mood_excited), icon = painterResource(Theme.icons.dueTone.flame)),
        SelectionOption(3, stringResource(id = R.string.mood_emotional), icon = painterResource(Theme.icons.dueTone.heart)),
        SelectionOption(4, stringResource(id = R.string.mood_curious), icon = painterResource(Theme.icons.dueTone.search))
    )
}

@Composable
fun getTimeOptions(): List<SelectionOption> {
    return listOf(
        SelectionOption(
            id = 1,
            label = stringResource(id = R.string.time_short),
            secondLabel = stringResource(id = R.string.time_short_description),
            icon = painterResource(Theme.icons.dueTone.timeShort)
        ),
        SelectionOption(
            id = 2,
            label = stringResource(id = R.string.time_medium),
            secondLabel = stringResource(id = R.string.time_medium_description),
            icon = painterResource(Theme.icons.dueTone.timeMedium)
        ),
        SelectionOption(
            id = 3,
            label = stringResource(id = R.string.time_long),
            secondLabel = stringResource(id = R.string.time_long_description),
            icon = painterResource(Theme.icons.dueTone.timeLong)
        )
    )
}
@Composable
fun getReleasePeriodOptions(): List<SelectionOption> {
    return listOf(
        SelectionOption(1, stringResource(id = R.string.ReleasePeriod_recent)),
        SelectionOption(2, stringResource(id = R.string.ReleasePeriod_classic)),
        SelectionOption(3, stringResource(id = R.string.ReleasePeriod_both))
    )
}