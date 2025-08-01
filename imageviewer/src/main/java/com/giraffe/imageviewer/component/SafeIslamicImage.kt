package com.giraffe.imageviewer.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.model.SafeIslamicImageHost

@NonRestartableComposable
@Composable
fun SafeIslamicImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    alignment: Alignment = Alignment.Center,
    placeHolder: @Composable (modifier: Modifier) -> Unit = { modifier ->
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = stringResource(id = R.string.placeholder),
            modifier = modifier
        )
    }
) {

    val context = LocalContext.current
    val host = remember { SafeIslamicImageHost(context.applicationContext) }

    LaunchedEffect(imageUrl) {
        host.loadImage(imageUrl, context)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (host.imageState == null) {
            placeHolder(modifier)

        } else {
        AsyncImage(
            model = host.imageState,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
            modifier = Modifier.fillMaxSize()
        )
            if (host.isBlurState) {
                Column(
                    Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_eye_slash),
                        contentDescription = stringResource(id = R.string.placeholder),
                        modifier = Modifier.size(20.dp),
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(Color(0xFFE1E1E3))
                    )
                    val finalStyle = TextStyle(
                        color = Color(0xFFE1E1E3),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        lineHeight = 12.sp,
                        letterSpacing = 0.sp
                    )
                    BasicText(
                        text = stringResource(R.string.sensitive_content),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = finalStyle
                    )

                }

            }
    }
}
}