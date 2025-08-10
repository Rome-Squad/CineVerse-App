package com.giraffe.match.screen.match_pager

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.components.ProgressIndicator
import com.giraffe.match.composable.ReadOnlyBlurWrapper
import com.giraffe.match.composable.SectionWithTitle
import com.giraffe.match.composable.SelectionPageTextOnly
import com.giraffe.match.composable.SelectionPageWithIcons
import com.giraffe.match.composable.SelectionPageWithIconsSingleRow
import com.giraffe.match.composable.getGenreOptions
import com.giraffe.match.composable.getMoodOptions
import com.giraffe.match.composable.getRecencyOptions
import com.giraffe.match.composable.getTimeOptions
import com.giraffe.presentation.match.R
import kotlinx.coroutines.launch

data class SelectionOption(
    val id: Int, val label: String, val secondLabel: String? = null, val icon: Painter? = null
)

@Composable
fun MatchPagerScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFinish: () -> Unit = {},
    viewModel: MatchPagerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = state.currentPage, pageCount = { 5 })
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MatchScreenEffect.NavigateBack -> onBackClick()
                MatchScreenEffect.FinishMatching -> onFinish()
            }
        }
    }

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.scrollToPage(state.currentPage)
        }
    }

    val moodOptions = getMoodOptions()
    val genreOptions = getGenreOptions()
    val timeOptions = getTimeOptions()
    val recencyOptions = getRecencyOptions()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AppBar(
            showBackButton = true,
            onBackButtonClick = {
                viewModel.onBackClicked()
            }
        )

        ProgressIndicator(
            progress = (state.currentPage + 1) / pagerState.pageCount.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.Top
                ) { page ->
                    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        when (page) {
                            0 -> {
                                SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                    SelectionPageWithIcons(
                                        options = moodOptions,
                                        selectedItems = state.moodSelections,
                                        onSelectionChange = viewModel::updateMoodSelections
                                    )
                                }
                            }

                            1 -> {
                                if (state.moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in state.moodSelections },
                                                selectedItems = state.moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                    SelectionPageTextOnly(
                                        options = genreOptions,
                                        selectedItems = state.genreSelections,
                                        onSelectionChange = viewModel::updateGenreSelections
                                    )
                                }
                            }

                            2 -> {
                                if (state.moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in state.moodSelections },
                                                selectedItems = state.moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in state.genreSelections },
                                                selectedItems = state.genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                    SelectionPageWithIconsSingleRow(
                                        options = timeOptions,
                                        selectedItems = state.timeSelections,
                                        onSelectionChange = viewModel::updateTimeSelections
                                    )
                                }
                            }

                            3 -> {
                                if (state.moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in state.moodSelections },
                                                selectedItems = state.moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in state.genreSelections },
                                                selectedItems = state.genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.timeSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                            SelectionPageWithIconsSingleRow(
                                                options = timeOptions.filter { it.id in state.timeSelections },
                                                selectedItems = state.timeSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.do_you_want_something_recent_or_classic)) {
                                    SelectionPageTextOnly(
                                        options = recencyOptions,
                                        selectedItems = state.recencySelections,
                                        onSelectionChange = viewModel::updateRecencySelections
                                    )
                                }
                            }

                            4 -> {
                                if (state.moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in state.moodSelections },
                                                selectedItems = state.moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in state.genreSelections },
                                                selectedItems = state.genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.timeSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                            SelectionPageWithIconsSingleRow(
                                                options = timeOptions.filter { it.id in state.timeSelections },
                                                selectedItems = state.timeSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (state.recencySelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.do_you_want_something_recent_or_classic)) {
                                            SelectionPageTextOnly(
                                                readOnly = true,
                                                options = recencyOptions.filter { it.id in state.recencySelections },
                                                selectedItems = state.recencySelections,
                                                onSelectionChange = {}
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.loading_recommendations_you_ll_love),
                                        style = Theme.textStyle.body.md.medium,
                                        color = Theme.color.shade.primary,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                text = if (state.currentPage < pagerState.pageCount - 2) {
                    stringResource(R.string.next)
                } else {
                    stringResource(R.string.start_matching)
                },
                isLoading = state.currentPage >= pagerState.pageCount - 2,
                onClick = {
                    coroutineScope.launch {
                        viewModel.onNextClicked()
                    }
                }
            )
        }
    }
}