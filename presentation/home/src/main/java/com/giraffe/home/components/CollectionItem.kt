package com.giraffe.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R

@Composable
fun CollectionItem(modifier: Modifier = Modifier, image: Int, collectionType: String) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(92.dp)
                .clip(RoundedCornerShape(Theme.radius.s))
                .background(Theme.color.shade.quinary)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .height(86.dp)
                .clip(RoundedCornerShape(Theme.radius.s))
                .background(Theme.color.brand.tertiary)
        )
        Box(
            Modifier
                .clip(RoundedCornerShape(Theme.radius.s))
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(image),
                contentDescription = stringResource(R.string.collection_item_image)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = Theme.color.overlay.primary)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                text = collectionType,
                style = Theme.textStyle.body.sm.medium,
                color = Theme.color.shade.primary
            )
        }
    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CollectionItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        CollectionItem(
//            modifier = Modifier.width(280.dp),
            image = R.drawable.collection_image,
            collectionType = "Text"
        )
    }
}