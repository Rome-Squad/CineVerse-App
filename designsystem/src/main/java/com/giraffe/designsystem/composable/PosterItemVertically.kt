package com.giraffe.designsystem.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.imageviewer.islamicimageviewer.IslamicAppropriateImageViewer

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PosterItemVertically(
    movie: PosterMovie,
    modifier: Modifier = Modifier,
    onClickPoster: () -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card)
                .clickable(onClick = onClickPoster)
                .aspectRatio(0.74f),
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
                IslamicAppropriateImageViewer(
                    imageUrl = movie.imageUri,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = stringResource(R.string.loading_image),
                    modifier = Modifier.size(32.dp),
                    tint = Theme.color.brand.secondary
                )
            }

            Rating(
                value = movie.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
            )
        }



        Text(
            text = movie.title,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme {
        PosterItemVertically(
            movie = PosterMovie(
                title = "The Flash",
                imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                rating = 7.5f,
            ),
            modifier = Modifier.width(156.dp)
        )
    }
}
