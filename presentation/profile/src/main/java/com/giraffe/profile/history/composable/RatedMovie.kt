package com.giraffe.profile.history.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.composable.PosterMovie
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RatedMovie(
    modifier: Modifier = Modifier,
    posterMovie: PosterMovie,
    onDeleteClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RateSection(
            modifier = Modifier.fillMaxWidth(),
            rate = posterMovie.rating.toDouble(),
            date = posterMovie.date ?: "--- --, 20--"
        )
        SwipableItem(
            actionButton = { DeleteButton(onDeleteClick = onDeleteClick) },
        ) {
            PosterItemHorizontal(
                modifier = modifier.fillMaxWidth(),
                movie = posterMovie,
            )
        }
    }
}

@Composable
private fun DeleteButton(modifier: Modifier = Modifier, onDeleteClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .size(48.dp)
            .background(
                color = Theme.color.additional.primary.red,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(onClick = onDeleteClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(20.dp),
            painter = painterResource(Theme.icons.dueTone.trash),
            contentDescription = "trash"
        )
    }
}

@Composable
private fun SwipableItem(
    modifier: Modifier = Modifier,
    actionButton: @Composable BoxScope.() -> Unit,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var cardWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged {
                    cardWidth = it.width.toFloat()
                },
            contentAlignment = Alignment.Center
        ) {
            actionButton()
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(-offset.value.roundToInt(), 0) }
                .pointerInput(true) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value - dragAmount).coerceIn(0f, cardWidth)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value >= cardWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(cardWidth)
                                        onExpanded()
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                        onCollapsed()
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}

@Composable
private fun RateSection(modifier: Modifier = Modifier, rate: Double, date: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "You gave it",
                style = Theme.textStyle.body.sm.medium,
                color = Theme.color.shade.primary
            )
            Stars(rate = rate)
        }
        Text(
            text = "on $date",
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.primary
        )
    }
}

@Composable
private fun Stars(modifier: Modifier = Modifier, rate: Double) {
    var remainRate = rememberSaveable { rate.toInt() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            val iconRes = if (remainRate > 0) Theme.icons.bold.star else Theme.icons.outline.star
            val iconTint =
                if (remainRate > 0) Theme.color.additional.primary.yellow else Theme.color.shade.tertiary
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(iconRes),
                contentDescription = "star",
                tint = iconTint,
            )
            remainRate--
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val posterMovie = PosterMovie(
        title = "The Flash",
        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
        rating = 7.5f,
        genres = "Drama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, Thriller",
        time = "2h 32m",
        date = "2008, Jul 18"
    )
    CineVerseTheme {
        //Stars(rate = 8.0)
        //RateSection(rate = 4.0, date = "Apr 12, 2025")
        RatedMovie(modifier = Modifier.width(328.dp), posterMovie)
    }
}