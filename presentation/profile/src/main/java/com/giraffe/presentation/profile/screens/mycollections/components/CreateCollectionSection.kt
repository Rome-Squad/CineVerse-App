package com.giraffe.presentation.profile.screens.mycollections.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.DefaultTextField

@Composable
fun CreateCollectionSection(
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
            enableCreate = value.isNotBlank()
        )
    }
}