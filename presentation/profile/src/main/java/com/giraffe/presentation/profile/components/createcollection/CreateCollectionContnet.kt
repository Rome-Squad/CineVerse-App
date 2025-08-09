package com.giraffe.presentation.profile.components.createcollection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.DefaultTextField

@Composable
fun CreateCollectionContent(
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CreateCollectionContent(
        startIcon = R.drawable.outline_folder,
        hintText = "e.g. My Watchlist",
        value = "",
        title = "Collection Name",
        onValueChange = {},
        createButtonClick = {},
        cancelButtonClick = {}
    )
}