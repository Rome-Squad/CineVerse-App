package com.giraffe.presentation.profile.screens.mycollections

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.Theme
import com.giraffe.media.collections.entity.Collection
import com.giraffe.presentation.profile.components.BaseScreen
import com.giraffe.presentation.profile.screens.mycollections.components.CreateCollectionSection
import com.giraffe.presentation.profile.screens.mycollections.components.NoCollectionsPlaceholder
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.toStringResource
import com.giraffe.presentation.profile.R as ProfileResources

@Composable
fun MyCollectionsScreen(
    navigateBack: () -> Unit = {},
    navigateToCollection: (Collection) -> Unit = {},
    navigateToExploreScreen: () -> Unit = {},
    viewModel: MyCollectionsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            MyCollectionsEffect.NavigateToBack -> navigateBack()

            is MyCollectionsEffect.NavigateToCollection -> navigateToCollection(effect.collection)

            MyCollectionsEffect.NavigateToExplore -> navigateToExploreScreen()

            is MyCollectionsEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.error.toStringResource()),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    MyCollectionsScreenContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun MyCollectionsScreenContent(
    state: MyCollectionsScreenState,
    interaction: MyCollectionsInteractionListener
) {
    BaseScreen(
        title = stringResource(R.string.my_collections),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.collections.isEmpty()) {
                NoCollectionsPlaceholder(
                    modifier = Modifier
                        .padding(60.dp)
                        .align(Alignment.Center),
                    onStartCollectingClick = interaction::onStartCollectingClick
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
                            modifier = Modifier.fillMaxWidth(),
                            text = collection.name,
                            description = stringResource(
                                ProfileResources.string.movies_count,
                                collection.itemCount
                            ),
                            icon = R.drawable.due_tone_folder,
                            onClick = {
                                interaction.onCollectionClick(collection)
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
                            interaction.onCreateNewCollectionClick()
                        }
                    )
                    .background(Theme.color.brand.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Theme.icons.outline.add),
                    contentDescription = stringResource(
                        id = ProfileResources.string.create_new_collection
                    ),
                    tint = Theme.color.button.onPrimary
                )
            }
            BaseBottomSheet(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 36.dp),
                title = stringResource(ProfileResources.string.create_new_collection),
                isVisible = state.isBottomSheetVisible,
                onDismiss = interaction::onDismissBottomSheet,
                content = {
                    CreateCollectionSection(
                        startIcon = R.drawable.outline_folder,
                        hintText = stringResource(
                            id = ProfileResources.string.collection_name
                        ),
                        value = state.newCollectionName,
                        title = stringResource(
                            id = ProfileResources.string.collection_name
                        ),
                        onValueChange = interaction::onNewCollectionNameChange,
                        createButtonClick = interaction::onConfirmCreateNewCollectionClick,
                        cancelButtonClick = interaction::onDismissBottomSheet
                    )
                }
            )
        }
    }
}