package com.giraffe.match.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.R
import com.giraffe.match.model.CarouselItem
import com.giraffe.match.utils.calculateCenterItem


@Composable
fun HeroCarousel(
    modifier: Modifier = Modifier, items: List<CarouselItem>
) {
    val listState = rememberLazyListState()
    val heroIndex = remember { mutableIntStateOf(0) }
    LaunchedEffect(listState) {
        snapshotFlow {
            Pair(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
        }.collect {
            heroIndex.intValue = calculateCenterItem(listState)
        }
    }

    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = 40.dp),
        horizontalArrangement = Arrangement.spacedBy((-20).dp)
    ) {
        itemsIndexed(items) { index, item ->
            val isHero = index == heroIndex.intValue
            CarouselItemContent(item, isHero)
        }
    }
}



@Composable
private fun CarouselItemContent(item: CarouselItem, isHero: Boolean) {
    val alpha by animateFloatAsState(targetValue = if (isHero) 1f else 0.6f)
    val width by animateDpAsState(
        targetValue = if (isHero) 240.dp else 200.dp, animationSpec = spring(
            dampingRatio = 15f, stiffness = 100f
        )
    )
    val height by animateDpAsState(
        targetValue = if (isHero) 320.dp else 267.dp, animationSpec = spring(
            dampingRatio = 15f, stiffness = 100f
        )
    )
    val offsetY by animateDpAsState(
        targetValue = if (isHero) (-25).dp else 0.dp, animationSpec = spring(
            dampingRatio = 15f, stiffness = 100f
        )
    )
    val cornerRadius = Theme.radius.xl

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .offset(y = offsetY)
            .graphicsLayer {
                this.alpha = alpha
                shape = RoundedCornerShape(cornerRadius)
                clip = true
            }
            .zIndex(if (isHero) 2f else 0f)
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (!isHero) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Theme.color.shade.primary.copy(0.4f))
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHeroCarouselAutoDetect() {

    val items: List<CarouselItem> = listOf(
        CarouselItem(R.drawable.p1),
        CarouselItem(R.drawable.p2),
        CarouselItem(R.drawable.p3),
        CarouselItem(R.drawable.p4),
        CarouselItem(R.drawable.pp1)
    )

    HeroCarousel(
        items = items
    )
}
