package com.giraffe.profile.components


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.profile.screens.history.PosterUiState

@Composable
fun PosterHorizontal(
    poster: PosterUiState,
    modifier: Modifier = Modifier,
    onSwiped: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = onSwiped),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {


            SafeIslamicImage(
                imageUrl = poster.imageUri,
                contentDescription = poster.name,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg
                        )
                    )

            )



            Column(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = poster.name,
                            style = Theme.textStyle.body.md.medium,
                            color = Theme.color.shade.primary,
                            maxLines = 1,
                            modifier = Modifier

                        )
                        Text(
                            text = poster.genres ?: stringResource(R.string.unknown_genre),
                            style = Theme.textStyle.body.sm.regular,
                            color = Theme.color.shade.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Rating(
                        value = poster.rating,
                        modifier = Modifier.padding(end=12.dp)
                    )
                }
                poster.date?.let {
                    if (it.isNotEmpty()) {
                        IconWithText(
                            icon = painterResource(Theme.icons.dueTone.calendar),
                            text = poster.date ?: stringResource(R.string.unknown_date)
                        )

                    }
                }


            }
        }
    }


@Composable
private fun IconWithText(icon: Painter, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.clock_icon),
            tint = Theme.color.shade.secondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary
        )
    }
}