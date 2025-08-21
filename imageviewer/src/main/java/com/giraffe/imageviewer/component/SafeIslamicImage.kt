package com.giraffe.imageviewer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.model.ImageState
import com.giraffe.imageviewer.model.SafeIslamicImageHost
import com.giraffe.imageviewer.model.StrengthLevel

@Composable
fun SafeIslamicImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    alignment: Alignment = Alignment.Center,
    hasSensitiveText: Boolean = true,
    placeholderModifier: Modifier = Modifier,
    placeholderTextStyle: TextStyle,
    eyeSplashTint: Color = Color(0xFFE1E1E3),
    placeholderIcon: Painter = painterResource(id = R.drawable.placeholder),
    placeHolderTint: Color = Color(0xFFBEC8FF),
    strengthLevel: StrengthLevel
) {
    val context = LocalContext.current
    val host = remember { SafeIslamicImageHost(context.applicationContext) }

    LaunchedEffect(imageUrl) {
        host.loadImage(imageUrl, strengthLevel)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val state = host.imageState) {
            is ImageState.Loading -> {
                Placeholder(placeholderIcon, placeholderModifier, placeHolderTint)
            }

            is ImageState.Success -> {
                AsyncImage(
                    model = state.bitmap,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    alignment = alignment,
                    modifier = Modifier.fillMaxSize()
                )

                if (state.isBlurred) {
                    Column(
                        Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_eye_slash),
                            contentDescription = stringResource(id = R.string.placeholder),
                            modifier = Modifier.size(20.dp),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = ColorFilter.tint(eyeSplashTint)
                        )
                        if (hasSensitiveText) {
                            BasicText(
                                text = stringResource(R.string.sensitive_content),
                                style = placeholderTextStyle,
                            )
                        }
                    }
                }
            }

            is ImageState.Error -> {
                Placeholder(placeholderIcon, placeholderModifier, placeHolderTint)
            }
        }
    }
}

@Composable
private fun Placeholder(
    icon: Painter,
    modifier: Modifier,
    tint: Color,
) {
    Image(
        painter = icon,
        colorFilter = ColorFilter.tint(tint),
        contentDescription = stringResource(id = R.string.placeholder),
        modifier = modifier.wrapContentSize()
    )
}
