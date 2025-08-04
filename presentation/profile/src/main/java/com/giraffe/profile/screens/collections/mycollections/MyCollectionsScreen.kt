package com.giraffe.profile.screens.collections.mycollections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.media.collections.entity.Collection
import com.giraffe.profile.screens.collections.mycollections.components.NoCollectionsPlaceholder
import com.giraffe.profile.utils.EffectListener

@Composable
fun MyCollectionsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToCollection: (Collection) -> Unit = {},
    navigateToExploreScreen: () -> Unit = {},
    viewModel: MyCollectionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val isNoInternet by viewModel.isNoInternet.collectAsState()
    val interactions: MyCollectionsInteractionListener = viewModel

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when(effect) {
            MyCollectionsEffect.NavigateBack -> {
                navigateBack()
            }
            is MyCollectionsEffect.NavigateToCollection -> {
                navigateToCollection(effect.collection)
            }
            MyCollectionsEffect.NavigateToExplore -> {
                navigateToExploreScreen()
            }
            is MyCollectionsEffect.ShowError -> {
                //TODO
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppBar(
            title = stringResource(R.string.my_collections),
            showBackButton = true,
            onBackButtonClick = interactions::onBackClick
        )

        if (isNoInternet) {
            NoInternetScreen()
        }

        if (!isNoInternet) {
            MyCollectionsScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                interactions = viewModel
            )
        }

    }
}

@Composable
private fun MyCollectionsScreenContent(
    modifier: Modifier = Modifier,
    state: MyCollectionsScreenState,
    interactions: MyCollectionsInteractionListener
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        if (state.collections.isEmpty()) {
            NoCollectionsPlaceholder(
                onStartCollectingClick = interactions::onStartCollectingClick
            )
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(
                        Alignment.TopCenter
                    ),
                contentPadding = PaddingValues(
                    all = 16.dp
                )
            ) {
                items(state.collections) { collection ->
                    CollectionItem(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    interactions.onCollectionClick(collection)
                                }
                            ),
                        text = collection.name,
                        description = collection.description,
                        icon = R.drawable.due_tone_folder
                    )
                }
            }

        }

        Box(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .background(Theme.color.brand.primary)
                .clickable(
                    onClick = {
                        interactions.onCreateNewCollectionClick()
                    }
                )
                .clip(
                    shape = RoundedCornerShape(Theme.radius.lg)
                )

        ) {
            Icon(
                painter = painterResource(Theme.icons.outline.add),
                contentDescription = stringResource(
                    id = com.giraffe.profile.R.string.create_new_collection
                ),
                tint = Theme.color.button.onPrimary
            )
        }
    }


}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewMyCollectionsScreen() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        MyCollectionsScreen(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewMyCollectionsScreenDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        MyCollectionsScreen(
            modifier = Modifier
        )
    }
}
