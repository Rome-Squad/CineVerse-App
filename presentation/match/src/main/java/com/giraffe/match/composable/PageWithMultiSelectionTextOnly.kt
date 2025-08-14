package com.giraffe.match.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.match.components.SelectionItem
import com.giraffe.match.model.SelectionType
import com.giraffe.match.screen.match_pager.SelectionOption

@Composable
fun PageWithMultiSelectionTextOnly(
    options: List<SelectionOption>,
    selectedItems: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    readOnly: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (readOnly && selectedItems.size == 1) {
            val selectedOption = options.firstOrNull { it.id == selectedItems.first() }
            selectedOption?.let { option ->
                SelectionItem(
                    type = SelectionType.CHIP,
                    description = option.label,
                    isSelected = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    onClick = {
                        if (!readOnly) {
                            onSelectionChange(emptyList())
                        }
                    }
                )
            }
        } else {
            options.chunked(3).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { option ->
                        SelectionItem(
                            type = SelectionType.CHIP,
                            description = option.label,
                            isSelected = option.id in selectedItems,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            onClick = {
                                if (!readOnly) {
                                    val current = selectedItems.toMutableList()
                                    if (option.id in current) current.remove(option.id) else current.add(option.id)
                                    onSelectionChange(current)
                                }
                            }
                        )
                    }
                    if (row.size < 3) repeat(3 - row.size) { Spacer(modifier = Modifier.weight(1f)) }
                }
            }
        }
    }
}
