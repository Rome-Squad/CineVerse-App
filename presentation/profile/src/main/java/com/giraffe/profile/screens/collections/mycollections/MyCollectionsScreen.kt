package com.giraffe.profile.screens.collections.mycollections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.media.collections.entity.Collection
import com.giraffe.profile.components.createcollection.CreateCollectionContent
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
        when (effect) {
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
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        if (state.collections.isEmpty() && state.isLoading.not()) {
            NoCollectionsPlaceholder(
                modifier = Modifier
                    .padding(60.dp)
                    .align(Alignment.Center),
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
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.collections) { collection ->
                    CollectionItem(
                        modifier = Modifier,
                        text = collection.name,
                        description = context.getString(
                            com.giraffe.profile.R.string.movies_count,
                            collection.itemCount
                        ),
                        icon = R.drawable.due_tone_folder,
                        onClick = {
                            interactions.onCollectionClick(collection)
                        }
                    )
                }
            }

        }

        Box(
            modifier = Modifier
                .padding(end = 24.dp, bottom = 36.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .clip(
                    shape = RoundedCornerShape(Theme.radius.lg)
                )
                .clickable(
                    onClick = {
                        interactions.onCreateNewCollectionClick()
                    }
                )
                .background(Theme.color.brand.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Theme.icons.outline.add),
                contentDescription = stringResource(
                    id = com.giraffe.profile.R.string.create_new_collection
                ),
                tint = Theme.color.button.onPrimary
            )
        }

        // add bottom sheet
        BaseBottomSheet(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 36.dp),
            title = stringResource(com.giraffe.profile.R.string.create_new_collection),
            isVisible = state.isCreateNewCollectionBottomSheetVisible,
            onDismiss = interactions::onDismissCreateNewCollectionBottomSheet,
            content = {
                CreateCollectionContent(
                    startIcon = R.drawable.outline_folder,
                    hintText = stringResource(
                        id = com.giraffe.profile.R.string.collection_name
                    ),
                    value = state.newCollectionName,
                    title = stringResource(
                        id = com.giraffe.profile.R.string.collection_name
                    ),
                    onValueChange = interactions::onNewCollectionNameChange,
                    createButtonClick = interactions::onConfirmCreateNewCollectionClick,
                    cancelButtonClick = interactions::onDismissCreateNewCollectionBottomSheet
                )
            }
        )
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
