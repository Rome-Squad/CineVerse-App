package com.giraffe.presentation.details.components.collectionBottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.giraffe.designsystem.R as DesignSystemResources
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.createCollection.CreateCollectionContent
import com.giraffe.presentation.details.model.CollectionUi
import com.giraffe.presentation.details.screens.moviedetails.MovieDetailsScreenState

@Composable
fun MovieCollectionsBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    targetState: MovieDetailsScreenState.CollectionBottomSheet?,
    collections: List<CollectionUi>,
    onNewCollectionClick: () -> Unit,
    onCollectionClick: (Int) -> Unit,
    onCreateCollectionButtonClick: () -> Unit,
    newCollectionName: String,
    onNewCollectionNameChange: (String) -> Unit,
    onConfirmCreateNewCollectionClick: () -> Unit,
    onCancelCreateNewCollectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        title = stringResource(R.string.add_to_collection),
        modifier = modifier,
        content = {
            AnimatedContent(
                targetState = targetState,
                transitionSpec = {
                    slideInHorizontally { it } togetherWith slideOutVertically { -it }
                }
            ) { collectionBottomSheet ->
                when (collectionBottomSheet) {
                    is MovieDetailsScreenState.CollectionBottomSheet.AddToCollection -> {
                        AddToCollectionBottomSheetContent(
                            collectionsList = collections,
                            onNewCollectionClick = onNewCollectionClick,
                            onCollectionClick = onCollectionClick
                        )
                    }

                    is MovieDetailsScreenState.CollectionBottomSheet.NoCollections -> {
                        NoCollectionsBottomSheetContent(
                            onCreateCollectionClick = onCreateCollectionButtonClick
                        )
                    }

                    is MovieDetailsScreenState.CollectionBottomSheet.CreateCollection -> {
                        CreateCollectionContent(
                            startIcon = DesignSystemResources.drawable.outline_folder,
                            hintText = stringResource(R.string.collection_name),
                            value = newCollectionName,
                            title = stringResource(R.string.collection_name),
                            onValueChange = onNewCollectionNameChange,
                            createButtonClick = onConfirmCreateNewCollectionClick,
                            cancelButtonClick = onCancelCreateNewCollectionClick
                        )
                    }

                    else -> Unit
                }
            }
        },
    )
}