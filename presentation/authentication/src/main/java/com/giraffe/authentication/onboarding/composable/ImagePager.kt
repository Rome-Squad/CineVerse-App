package com.giraffe.authentication.onboarding.composable

import android.annotation.SuppressLint
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R
import kotlin.math.absoluteValue

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    images: List<Int>,
) {
    val layoutDirection = LocalLayoutDirection.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = screenWidthDp * 0.1f

    HorizontalPager(
        state = pagerState,
        pageSpacing = 24.dp,
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
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = stringResource(R.string.rotationanim)
        )


        val scale = lerp(1f, 0.8f, pageOffsetAbsolute)

        val shape = animatedCornerShape(
            pageOffsetAbsolute = pageOffsetAbsolute,
            pageIndexDiff = pageIndexDiff
        )
        val configuration = LocalConfiguration.current
        val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

        val imageAspectRatio = if (isPortrait) 3f / 4.5f else 16f / 9f

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = stringResource(R.string.image, page + 1),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .graphicsLayer {
                        this.rotationZ = animatedRotationZ
                        this.scaleX = scale
                        this.scaleY = scale
                    }
                    .aspectRatio(imageAspectRatio)
                    .clip(shape)
                    .border(
                        width = 1.dp,
                        brush = Theme.color.stroke.glow,
                        shape = shape
                    )
            )
        }
    }
}

@Composable
private fun animatedCornerShape(
    pageOffsetAbsolute: Float,
    pageIndexDiff: Int,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300, easing = FastOutSlowInEasing)
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
        animationSpec = animationSpec,
        label = stringResource(R.string.topradiusanim)
    )

    val animatedBottomRadius by animateDpAsState(
        targetValue = targetBottomRadius,
        animationSpec = animationSpec,
        label = stringResource(R.string.bottomradiusanim)
    )

    return RoundedCornerShape(
        topStart = animatedTopRadius,
        topEnd = animatedTopRadius,
        bottomStart = animatedBottomRadius,
        bottomEnd = animatedBottomRadius
    )
}