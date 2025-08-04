package com.giraffe.profile.screens.collections.mycollections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.media.collections.entity.Collection

@Composable
fun MyCollectionsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    navigateToCollection: (Collection) -> Unit = {},
    navigateToExploreScreen: () -> Unit = {},
    viewModel: MyCollectionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val isNoInternet by viewModel.isNoInternet.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppBar(
            showBackButton = true,
            onBackButtonClick = onBackClick
        )

        if(isNoInternet) {
            NoInternetScreen( )
        }

        if (!isNoInternet){
            MyCollectionsScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                navigateToCollection = navigateToCollection,
                navigateToExploreScreen = navigateToExploreScreen,
                interactions = viewModel
            )
        }

    }
}

@Composable
private fun MyCollectionsScreenContent(
    modifier: Modifier = Modifier,
    state: MyCollectionsScreenState,
    navigateToCollection: (Collection) -> Unit = {},
    navigateToExploreScreen: () -> Unit = {},
    interactions: MyCollectionsInteractionListener
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
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
                        .clickable (
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
