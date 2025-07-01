package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun PosterColumnCard(
    posterRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(248.dp)
            .height(377.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Theme.color.background.screen)
            .padding(top = 16.dp, bottom = 16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainMoviePoster(
            posterUrl = posterRes,
            modifier = Modifier
                .width(216.dp)
                .weight(1f)
                .clip(RoundedCornerShape(Theme.radius.xl))
        )

        MiniMoviePoster(
            posterUrl = posterRes
        )
    }
}

@Composable
fun MainMoviePoster(
    posterUrl: Int,
    modifier: Modifier = Modifier
) {
    Image(  modifier = modifier
        .clip(RoundedCornerShape(Theme.radius.xl)),
        painter = rememberAsyncImagePainter(model = posterUrl),
        contentDescription = "Main Movie Poster",
        contentScale = ContentScale.Crop,

        )
}

@Composable
fun MiniMoviePoster(
    posterUrl: Int,
    modifier: Modifier = Modifier
) {
    Image(

        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(Theme.radius.full)),
        painter = rememberAsyncImagePainter(model = posterUrl),
        contentDescription = "Mini Movie Poster",
        contentScale = ContentScale.Crop,

        )
}

@Preview( showBackground = true,
    showSystemUi = false)
@Composable
fun PosterColumnCardPreview() {
    CineVerseTheme (
        isDarkTheme = true
    ) {
        PosterColumnCard(

            posterRes = R.drawable.main_poster_test,

            )
    }
}