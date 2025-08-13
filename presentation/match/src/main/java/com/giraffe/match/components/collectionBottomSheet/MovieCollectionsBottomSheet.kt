package com.giraffe.match.components.collectionBottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.match.components.createCollection.CreateCollectionContent
import com.giraffe.match.screen.result.CollectionUi
import com.giraffe.match.screen.result.MatchResultScreenState
import com.giraffe.presentation.match.R
import com.giraffe.designsystem.R as DesignSystemResources

@Composable
fun MovieCollectionsBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    targetState: MatchResultScreenState.CollectionBottomSheet?,
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
                    is MatchResultScreenState.CollectionBottomSheet.AddToCollection -> {
                        AddToCollectionBottomSheetContent(
                            collectionsList = collections,
                            onNewCollectionClick = onNewCollectionClick,
                            onCollectionClick = onCollectionClick
                        )
                    }

                    is MatchResultScreenState.CollectionBottomSheet.NoCollections -> {
                        NoCollectionsBottomSheetContent(
                            onCreateCollectionClick = onCreateCollectionButtonClick
                        )
                    }

                    is MatchResultScreenState.CollectionBottomSheet.CreateCollection -> {
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

                    null -> Unit
                }
            }
        }
    )
}
