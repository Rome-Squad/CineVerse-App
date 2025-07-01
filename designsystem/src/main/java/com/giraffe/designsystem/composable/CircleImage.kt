package com.giraffe.designsystem.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun CircleImage(
    imageUri: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.color.background.card)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {

        val model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()

        val painter = rememberAsyncImagePainter(model = model)

        when (painter.state.collectAsState().value) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.circle_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            is AsyncImagePainter.State.Empty -> {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.profile),
                    contentDescription = stringResource(R.string.image_is_empty),
                    modifier = Modifier.size(32.dp),
                    tint = Theme.color.shade.secondary
                )
            }

            else -> {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = stringResource(R.string.loading_image),
                    modifier = Modifier.size(32.dp),
                    tint = Theme.color.brand.secondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CineVerseTheme {
        CircleImage(
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            modifier = Modifier.size(56.dp)
        )
    }
}