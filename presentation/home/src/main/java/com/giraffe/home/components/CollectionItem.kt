package com.giraffe.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

data class CollectionItemData(
    val image: Int,
    val collectionType: String
)

@Composable
fun CollectionItem(modifier: Modifier = Modifier, collectionItemData: CollectionItemData) {
    Box(modifier = modifier) {
        Column {
            Box(
                Modifier
                    .clip(RoundedCornerShape(Theme.radius.s))
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Theme.radius.s)),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(collectionItemData.image),
                    contentDescription = stringResource(R.string.collection_item_image)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .align(Alignment.BottomCenter)
                        .background(color = Theme.color.overlay.primary)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = collectionItemData.collectionType,
                        style = Theme.textStyle.body.sm.medium,
                        color = Theme.color.shade.primary
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = Theme.radius.s,
                            bottomEnd = Theme.radius.s
                        )
                    )
                    .background(Theme.color.brand.tertiary)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = Theme.radius.s,
                            bottomEnd = Theme.radius.s
                        )
                    )
                    .background(Theme.color.shade.quinary)
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = false)
fun CollectionItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        CollectionItem(
            modifier = Modifier
                .background(Theme.color.shade.quaternary)
                .padding(16.dp)
                .width(280.dp),
            collectionItemData = CollectionItemData(
                image = R.drawable.collection_image,
                collectionType = "Text"
            )
        )
    }
}