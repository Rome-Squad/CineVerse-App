package com.giraffe.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.model.FeaturedCollectionType
import com.giraffe.presentation.home.model.FeaturedCollectionUi
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CollectionItem(
    modifier: Modifier = Modifier,
    collectionItemData: FeaturedCollectionUi,
    onClick: (CategoryMediaSectionType) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = modifier
            .widthIn(min = screenWidth * 0.5f, max = screenWidth * 0.75f)
            .clickable(onClick = dropUnlessResumed {
                onClick(
                    when (collectionItemData.type) {
                        FeaturedCollectionType.LATE_NIGHT_THRILLS -> CategoryMediaSectionType.LATE_NIGHT_THRILLS
                        FeaturedCollectionType.FAMILY_NIGHT_PICKS -> CategoryMediaSectionType.FAMILY_NIGHT_PICKS
                        FeaturedCollectionType.MIND_BENDING_STORIES -> CategoryMediaSectionType.MIND_BENDING_STORIES
                        FeaturedCollectionType.BASED_ON_TRUE_EVENTS -> CategoryMediaSectionType.BASED_ON_TRUE_EVENTS
                        FeaturedCollectionType.CINEMATIC_MASTERPIECE -> CategoryMediaSectionType.CINEMATIC_MASTERPIECE
                        FeaturedCollectionType.FEEL_GOOD_PREFERENCES -> CategoryMediaSectionType.FEEL_GOOD_PREFERENCES
                    }
                )
            })
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(Theme.radius.s))
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(collectionItemData.type.imageRes),
                contentDescription = stringResource(R.string.collection_item_image),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = Theme.color.overlay.primary)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(collectionItemData.type.titleRes),
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