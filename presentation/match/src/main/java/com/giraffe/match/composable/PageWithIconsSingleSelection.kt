package com.giraffe.match.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.match.components.SelectionItem
import com.giraffe.match.model.SelectionType
import com.giraffe.match.screen.match_pager.SelectionOption

@Composable
fun PageWithIconsSingleSelection(
    options: List<SelectionOption>,
    selectedItem: Int?,
    onSelectionChange: (Int) -> Unit,
    readOnly: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectionItem(
                    type = SelectionType.CARD,
                    description = option.label,
                    secondDescription = option.secondLabel,
                    icon = option.icon,
                    isSelected = option.id == selectedItem,
                    isSecondaryCardType = option.secondLabel != null,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (!readOnly) {
                            onSelectionChange(option.id)
                        }
                    }
                )
            }
        }
    }
}