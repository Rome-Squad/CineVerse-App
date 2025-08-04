package com.giraffe.onboarding.composable

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.giraffe.designsystem.theme.Theme
import kotlin.math.absoluteValue

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    images: List<Int>
) {
    val layoutDirection = LocalLayoutDirection.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = screenWidthDp * 0.1f


    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        modifier = modifier
    ) { page ->

        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val pageOffsetAbsolute = pageOffset.absoluteValue.coerceIn(0f, 1f)
        val pageIndexDiff = page - pagerState.currentPage

        val rotationZ = when {
            pageIndexDiff > 0 -> if (layoutDirection == LayoutDirection.Rtl) -12f else 12f
            pageIndexDiff < 0 -> if (layoutDirection == LayoutDirection.Rtl) 12f else -12f
            else -> 0f
        } * pageOffsetAbsolute

        val animatedRotationZ by animateFloatAsState(
            targetValue = rotationZ,
            label = "rotationAnim"
        )
        val scale = lerp(1f, 0.8f, pageOffsetAbsolute)

        val shape = animatedCornerShape(
            pageOffsetAbsolute = pageOffsetAbsolute,
            pageIndexDiff = pageIndexDiff
        )

        Image(
            painter = painterResource(id = images[page]),
            contentDescription = "Image ${page + 1}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .graphicsLayer {
                    this.rotationZ = animatedRotationZ
                    this.scaleX = scale
                    this.scaleY = scale
                }
                .fillMaxSize()
                .clip(shape)
                .border(
                    width = 1.dp,
                    brush = Theme.color.stroke.glow,
                    shape = shape
                )
        )
    }
}

@Composable
private fun animatedCornerShape(
    pageOffsetAbsolute: Float,
    pageIndexDiff: Int
): RoundedCornerShape {
    val isCurrentPage = pageOffsetAbsolute < 0.1f

    val targetTopRadius = remember { 24.dp }

    val targetBottomRadius = remember(pageOffsetAbsolute, pageIndexDiff) {
        when {
            isCurrentPage -> 0.dp
            pageIndexDiff != 0 -> lerp(0.dp, 24.dp, pageOffsetAbsolute)
            else -> 0.dp
        }
    }

    val animatedTopRadius by animateDpAsState(
        targetValue = targetTopRadius,
        label = "topAnim"
    )

    val animatedBottomRadius by animateDpAsState(
        targetValue = targetBottomRadius,
        label = "bottomAnim"
    )

    return RoundedCornerShape(
        topStart = animatedTopRadius,
        topEnd = animatedTopRadius,
        bottomStart = animatedBottomRadius,
        bottomEnd = animatedBottomRadius
    )
}