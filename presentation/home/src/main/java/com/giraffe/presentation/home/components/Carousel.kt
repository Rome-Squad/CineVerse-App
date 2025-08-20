package com.giraffe.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.dropUnlessResumed
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.utils.shimmerEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Carousel(
    popularMediaItems: List<PopularMediaUi>,
    onClickItem: (Int, MediaType) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    scrollDelayMillis: Long = 3000L
) {
    val pagerState =
        rememberPagerState(initialPage = 0) { if (popularMediaItems.isEmpty()) 5 else popularMediaItems.size }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val screenWidth = with(density) { configuration.screenWidthDp.dp }
    val width = screenWidth - 48.dp
    val initialHeight = width * (200f / 312f)


    LaunchedEffect(pagerState.currentPage) {
        delay(scrollDelayMillis)
        val nextIndex = (pagerState.currentPage + 1) % pagerState.pageCount
        scope.launch {
            pagerState.animateScrollToPage(nextIndex)
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = initialHeight + 68.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                beyondViewportPageCount = 2,
                pageSpacing = (-24).dp,
                contentPadding = PaddingValues(horizontal = 24.dp),
                state = pagerState,
            ) { pageIndex ->
                val pageOffset =
                    (pageIndex - pagerState.currentPage) + pagerState.currentPageOffsetFraction
                val progress = 1f - abs(pageOffset).coerceIn(0f, 1f)
                val extraHeight = 38.dp * (1f - progress)
                val dynamicHeight = initialHeight + extraHeight
                val offsetY = lerp(0.dp, (-48).dp, progress)
                val zIndex = progress

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = offsetY.toPx()
                        }
                        .width(width)
                        .height(dynamicHeight)
                        .zIndex(zIndex)
                        .clip(shape = RoundedCornerShape(Theme.radius.lg))
                        .shimmerEffect()
                        .align(Alignment.TopCenter),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .shimmerEffect(),
                )
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(88.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .shimmerEffect(),
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = initialHeight + 68.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                beyondViewportPageCount = 2,
                pageSpacing = (-24).dp,
                contentPadding = PaddingValues(horizontal = 24.dp),
                state = pagerState,
            ) { pageIndex ->
                val isSelected = pageIndex == pagerState.currentPage
                val popularMediaItem = popularMediaItems[pageIndex]

                val pageOffset =
                    (pageIndex - pagerState.currentPage) + pagerState.currentPageOffsetFraction
                val progress = 1f - abs(pageOffset).coerceIn(0f, 1f)

                val extraHeight = 38.dp * (1f - progress)
                val dynamicHeight = initialHeight + extraHeight

                val alpha = androidx.compose.ui.util.lerp(.7f, 1f, progress)
                val offsetY = lerp(0.dp, (-48).dp, progress)
                val zIndex = progress

                VODItem(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = offsetY.toPx()
                        }
                        .width(width)
                        .height(dynamicHeight)
                        .zIndex(zIndex)
                        .alpha(alpha)
                        .clip(shape = RoundedCornerShape(Theme.radius.lg))
                        .clickable(onClick = dropUnlessResumed {
                            onClickItem(
                                popularMediaItem.id,
                                popularMediaItem.mediaType
                            )
                        })
                        .align(Alignment.TopCenter),
                    popularMediaItem = popularMediaItem,
                    isSelected = isSelected
                )
            }

            AnimatedContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter),
                targetState = popularMediaItems[pagerState.currentPage],
            ) { movieCard ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = screenWidth - 48.dp)
                            .padding(horizontal = 50.dp),
                        text = movieCard.title,
                        style = Theme.textStyle.body.md.medium,
                        color = Theme.color.shade.primary,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = movieCard.genres.joinToString(separator = ", "),
                        style = Theme.textStyle.body.sm.regular,
                        color = Theme.color.shade.secondary,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}