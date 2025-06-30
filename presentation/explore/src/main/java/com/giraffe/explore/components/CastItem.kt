package com.giraffe.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    imageResId: Painter,
    name: String
) {
    BoxWithConstraints(modifier = modifier) {

        val imageHeight = maxHeight * 0.769f
        val spacerHeight = maxHeight * 0.077f
        val textHeight = maxHeight * 0.154f

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imageResId,
                contentDescription = name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(imageHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Theme.radius.lg))
            )

            Spacer(modifier = Modifier.height(spacerHeight))

            Text(
                modifier = Modifier
                    .height(textHeight)
                    .fillMaxWidth(),
                text = name,
                textAlign = TextAlign.Center,
                style = Theme.textStyle.body.sm.medium,
                color = Theme.color.shade.secondary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121321)
@Composable
fun CastItemPreview() {
    CineVerseTheme {
                CastItem(
                    Modifier.width(98.66667175292969.dp).height(122.66667175292969.dp),
                    imageResId = painterResource(id = Theme.icons.colored.instagram),
                    name =    "Tom Hardy"
                )
    }
}
