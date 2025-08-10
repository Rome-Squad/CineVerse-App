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
import kotlin.collections.forEach

@Composable
fun SelectionPageWithIconsSingleRow(
    options: List<SelectionOption>,
    selectedItems: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    readOnly: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectionItem(
                    type = SelectionType.CARD,
                    description = option.label,
                    secondDescription = option.secondLabel,
                    icon = option.icon,
                    isSelected = option.id in selectedItems,
                    isSecondaryCardType = option.secondLabel != null,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (!readOnly) {
                            val current = selectedItems.toMutableList()
                            if (option.id in current) current.remove(option.id) else current.add(
                                option.id
                            )
                            onSelectionChange(current)
                        }
                    }
                )
            }
        }
    }
}