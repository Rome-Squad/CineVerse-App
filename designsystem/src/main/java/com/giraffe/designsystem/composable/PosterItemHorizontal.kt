package com.giraffe.designsystem.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie

@Composable
fun PosterItemHorizontal(
    movie: PosterMovie,
    modifier: Modifier = Modifier,
    onClickPoster: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = onClickPoster),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
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
                .background(Theme.color.brand.tertiary),
            contentAlignment = Alignment.Center
        ) {
            val model = ImageRequest
                .Builder(LocalContext.current)
                .data(movie.imageUri)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build()

            val painter = rememberAsyncImagePainter(model = model)
            val state by painter.state.collectAsState()

            if (state is AsyncImagePainter.State.Success) {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.image_poster_movie),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = stringResource(R.string.loading_image),
                    modifier = Modifier.size(24.dp),
                    tint = Theme.color.brand.secondary
                )
            }
        }
    }


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
                    text = movie.title,
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.shade.primary
                )
                Text(
                    text = movie.genres ?: stringResource(R.string.unknown_genre),
                    style = Theme.textStyle.body.sm.regular,
                    color = Theme.color.shade.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Rating(
                value = movie.rating,
            )
        }




        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconWithText(
                icon = painterResource(Theme.icons.dueTone.clock),
                text = movie.time ?: stringResource(R.string.unknown_time)
            )
            IconWithText(
                icon = painterResource(Theme.icons.dueTone.calendar),
                text = movie.date ?: stringResource(R.string.unknown_date)
            )
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        PosterItemHorizontal(
            movie = PosterMovie(
                title = "The Flash",
                imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                rating = 7.5f,
                genres = "Drama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, Thriller",
                time = "2h 32m",
                date = "2008, Jul 18"
            ),
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .height(88.dp)
        )
    }
}