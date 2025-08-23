package com.giraffe.match.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.match.components.SelectionItem
import com.giraffe.match.screen.match_pager.SelectionOption

@Composable
fun SelectionPageWithIcons(
    options: List<SelectionOption>,
    selectedItems: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    readOnly: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        options.chunked(2).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { option ->
                    SelectionItem(
                        description = option.label,
                        icon = option.icon,
                        isSelected = option.id in selectedItems,
                        modifier = Modifier
                            .weight(1f),
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
                if (row.size < 2) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}