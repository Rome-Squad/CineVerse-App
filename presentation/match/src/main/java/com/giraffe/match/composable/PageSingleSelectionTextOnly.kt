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
fun SingleSelectionPageTextOnly(
    options: List<SelectionOption>,
    selectedItem: Int?,
    onSelectionChange: (Int?) -> Unit,
    readOnly: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (readOnly && selectedItem != null) {
            val selectedOption = options.firstOrNull { it.id == selectedItem }
            selectedOption?.let { option ->
                SelectionItem(
                    type = SelectionType.CHIP,
                    description = option.label,
                    isSelected = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        if (!readOnly) {
                            onSelectionChange(null)
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
                            isSelected = option.id == selectedItem,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            onClick = {
                                if (!readOnly) {
                                    onSelectionChange(
                                        if (selectedItem == option.id) null else option.id
                                    )
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