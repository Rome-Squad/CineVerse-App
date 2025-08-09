package com.giraffe.presentation.profile.screens.mycollections

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.CollectionItem
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.media.collections.entity.Collection
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.toStringResource
import com.giraffe.presentation.profile.R as ProfileResources

@Composable
fun MyCollectionsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateToCollection: (Collection) -> Unit = {},
    navigateToExploreScreen: () -> Unit = {},
    viewModel: MyCollectionsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val interactions: MyCollectionsInteractionListener = viewModel
    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            MyCollectionsEffect.NavigateBack -> navigateBack()

            is MyCollectionsEffect.NavigateToCollection -> navigateToCollection(effect.collection)

            MyCollectionsEffect.NavigateToExplore -> navigateToExploreScreen()

            is MyCollectionsEffect.ShowError -> Toast.makeText(
                context,
                context.getString(effect.error.toStringResource()),
                Toast.LENGTH_SHORT
            ).show()
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
        if (state.isNoInternet) {
            NoInternetScreen()
        }
        if (!state.isNoInternet) {
            MyCollectionsScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
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
                        modifier = Modifier.fillMaxWidth(),
                        text = collection.name,
                        description = context.getString(
                            ProfileResources.string.movies_count,
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
            onDismiss = interactions::onDismissCreateNewCollectionBottomSheet,
            content = {
                CreateCollectionContent(
                    startIcon = R.drawable.outline_folder,
                    hintText = stringResource(
                        id = ProfileResources.string.collection_name
                    ),
                    value = state.newCollectionName,
                    title = stringResource(
                        id = ProfileResources.string.collection_name
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
private fun CreateCollectionContent(
    modifier: Modifier = Modifier,
    startIcon: Int,
    hintText: String,
    value: String,
    title: String,
    onValueChange: (String) -> Unit,
    createButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DefaultTextField(
            startIcon = painterResource(startIcon),
            placeholder = hintText,
            modifier = Modifier,
            label = title,
            value = value,
            onValueChange = onValueChange,
        )
        CollectionsButtons(
            createButtonClick = createButtonClick,
            cancelButtonClick = cancelButtonClick,
            enableCreate = true
        )
    }
}

@Composable
private fun CollectionsButtons(
    modifier: Modifier = Modifier,
    createButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    enableCreate: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SecondaryButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            text = stringResource(ProfileResources.string.cancel),
            onClick = cancelButtonClick,
        )

        PrimaryButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            text = stringResource(ProfileResources.string.create),
            onClick = createButtonClick,
            enabled = enableCreate
        )
    }
}

@Composable
private fun NoCollectionsPlaceholder(
    modifier: Modifier = Modifier,
    onStartCollectingClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = 64.dp,
                    height = 80.dp
                )
                .padding(
                    bottom = 16.dp
                )
                .clip(CircleShape)
                .background(Theme.color.brand.tertiary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.videoLibrary),
                tint = Theme.color.shade.primary,
                contentDescription = stringResource(ProfileResources.string.no_collections),
            )
        }

        Text(
            text = stringResource(ProfileResources.string.no_collections_yet),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(
                    bottom = 8.dp
                )
        )


        Text(
            text = stringResource(ProfileResources.string.start_building_your_personal_library_by_saving_movies_or_series_you_want_to_remember),
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(
                    bottom = 24.dp
                )
        )

        PrimaryButton(
            text = stringResource(ProfileResources.string.start_collecting),
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.CenterHorizontally
                ),
            onClick = onStartCollectingClick
        )
    }
}