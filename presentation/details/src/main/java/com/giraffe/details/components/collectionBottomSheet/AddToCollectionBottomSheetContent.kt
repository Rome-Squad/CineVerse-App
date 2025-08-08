package com.giraffe.details.components.collectionBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.details.models.CollectionUi

@Composable
fun AddToCollectionBottomSheetContent(
    collectionsList: List<CollectionUi>,
    onCollectionClick: (Int) -> Unit,
    onNewCollectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(collectionsList) { index, collection ->
            AddToCollectionItem(
                title = collection.title,
                isLoading = collection.isLoading,
                onCollectionClicked = { onCollectionClick(collection.id) }
            )
        }
    }
}