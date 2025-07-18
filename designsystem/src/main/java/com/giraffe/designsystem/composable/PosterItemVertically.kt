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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.imageviewer.component.SafeIslamicImage

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PosterItemVertically(
    movie: Poster,
    modifier: Modifier = Modifier,
    onClickPoster: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card)
                .aspectRatio(0.74f),
            contentAlignment = Alignment.Center
        ) {
            SafeIslamicImage(
                imageUrl = movie.imageUri,
                contentDescription = movie.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onClickPoster)
            ) {
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
            text = movie.name,
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
            movie = Poster(
                id = 1,
                name = "The Flash",
                imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                rating = 7.5f,
            ),
            modifier = Modifier.width(156.dp)
        )
    }
}
