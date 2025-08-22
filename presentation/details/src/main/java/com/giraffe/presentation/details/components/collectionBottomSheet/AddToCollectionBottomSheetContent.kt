package com.giraffe.presentation.details.components.collectionBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.details.model.CollectionUi

@Composable
fun AddToCollectionBottomSheetContent(
    collectionsList: List<CollectionUi>,
    onCollectionClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            collectionsList,
            key = { _, collection -> collection.id }
        ) { index, collection ->
            AddToCollectionItem(
                title = collection.title,
                isLoading = collection.isLoading,
                isChecked = collection.isChecked,
                onCollectionClicked = { onCollectionClick(collection.id) }
            )
        }
    }
}