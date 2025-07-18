package com.giraffe.designsystem.composable.custom

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.giraffe.designsystem.theme.Theme

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape,
    colors: Color = Theme.color.background.card,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = colors),
        content = content
    )
}
