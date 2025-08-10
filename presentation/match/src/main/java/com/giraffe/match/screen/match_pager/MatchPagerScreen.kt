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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.match.R
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SelectionOption(
    val id: Int, val label: String, val secondLabel: String? = null, val icon: Painter? = null
)

@Composable
fun MatchPagerScreen(
    modifier: Modifier = Modifier, onBackClick: () -> Unit = {}, onFinish: () -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    var moodSelections by remember { mutableStateOf(listOf<Int>()) }
    var genreSelections by remember { mutableStateOf(listOf<Int>()) }
    var timeSelections by remember { mutableStateOf(listOf<Int>()) }
    var recencySelections by remember { mutableStateOf(listOf<Int>()) }

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
            showBackButton = true, onBackButtonClick = {
                if (pagerState.currentPage > 0) {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                } else onBackClick()
            })

        ProgressIndicator(
            progress = (pagerState.currentPage + 1) / pagerState.pageCount.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
        val scrollState = rememberScrollState()

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
                                        selectedItems = moodSelections,
                                        onSelectionChange = { moodSelections = it })
                                }
                            }

                            1 -> {
                                if (moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in moodSelections },
                                                selectedItems = moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                    SelectionPageTextOnly(
                                        options = genreOptions,
                                        selectedItems = genreSelections,
                                        onSelectionChange = { genreSelections = it })
                                }
                            }

                            2 -> {
                                if (moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in moodSelections },
                                                selectedItems = moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in genreSelections },
                                                selectedItems = genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                    SelectionPageWithIconsSingleRow(
                                        options = timeOptions,
                                        selectedItems = timeSelections,
                                        onSelectionChange = { timeSelections = it })
                                }
                            }

                            3 -> {
                                if (moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in moodSelections },
                                                selectedItems = moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in genreSelections },
                                                selectedItems = genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (timeSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                            SelectionPageWithIconsSingleRow(
                                                options = timeOptions.filter { it.id in timeSelections },
                                                selectedItems = timeSelections,  // no highlight
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                SectionWithTitle(stringResource(R.string.do_you_want_something_recent_or_classic)) {
                                    SelectionPageTextOnly(
                                        options = recencyOptions,
                                        selectedItems = recencySelections,
                                        onSelectionChange = { recencySelections = it })
                                }
                            }

                            4 -> {
                                if (moodSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.what_mood_are_you_in)) {
                                            SelectionPageWithIcons(
                                                options = moodOptions.filter { it.id in moodSelections },
                                                selectedItems = moodSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }


                                }
                                if (genreSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.pick_a_genre_you_love)) {
                                            SelectionPageTextOnly(
                                                options = genreOptions.filter { it.id in genreSelections },
                                                selectedItems = genreSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (timeSelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.how_much_time_do_you_have)) {
                                            SelectionPageWithIconsSingleRow(
                                                options = timeOptions.filter { it.id in timeSelections },
                                                selectedItems = timeSelections,
                                                onSelectionChange = {},
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                                if (recencySelections.isNotEmpty()) {
                                    ReadOnlyBlurWrapper(readOnly = true) {
                                        SectionWithTitle(stringResource(R.string.do_you_want_something_recent_or_classic)) {
                                            SelectionPageTextOnly(
                                                readOnly = true,
                                                options = recencyOptions.filter { it.id in recencySelections },
                                                selectedItems = recencySelections,
                                                onSelectionChange = {})
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
                text = if (pagerState.currentPage < pagerState.pageCount - 2) stringResource(R.string.next) else stringResource(
                    R.string.start_matching
                ),
                isLoading = isLoading,
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            isLoading = true
                            delay(1500)
                            isLoading = false
                            onFinish()
                        }
                    }
                })
        }

    }
}
