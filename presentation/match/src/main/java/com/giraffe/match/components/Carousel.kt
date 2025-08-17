package com.giraffe.match.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroCarousel(
    modifier: Modifier = Modifier,
    items: List<String>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 48.dp),
    onPageChanged: (Int) -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(0) { items.size }
    val density = LocalDensity.current

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }
    val screenWidth = with(density) { LocalWindowInfo.current.containerSize.width.toDp() }
    val width = screenWidth - 48.dp
    val posterAspectRatio = 4f / 5f
    val initialHeight = width / posterAspectRatio

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = initialHeight + 68.dp)
    ) {
        HorizontalPager(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            beyondViewportPageCount = items.size,
            pageSpacing = (-24).dp,
            contentPadding = contentPadding,
            state = pagerState,
        ) { pageIndex ->
            val isSelected = pageIndex == pagerState.currentPage
            val imageId = items[pageIndex]

            val pageOffset =
                (pageIndex - pagerState.currentPage) + pagerState.currentPageOffsetFraction
            val progress = 1f - abs(pageOffset).coerceIn(0f, 1f)

            val extraHeight = 38.dp * (1f - progress)
            val dynamicHeight = initialHeight - extraHeight


            val alpha = lerp(.7f, 1f, progress)
            val zIndex = progress

            CarouselItemContent(
                modifier = Modifier
                    .width(width)
                    .height(dynamicHeight)
                    .zIndex(zIndex)
                    .alpha(alpha)
                    .clip(shape = RoundedCornerShape(Theme.radius.lg))
                    .align(Alignment.TopCenter)
                    .clickable { onItemClick(pageIndex) },
                imageUrl = imageId,
                isSelected = isSelected
            )
        }
    }
}

@Composable
private fun CarouselItemContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(Theme.radius.lg))
    ) {
        SafeIslamicImage(
            imageUrl = imageUrl,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(Theme.radius.lg))
        )

        if (!isSelected) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Theme.color.shade.primary.copy(0.4f))
            )
        }
    }
}