package com.giraffe.profile.components.mycollections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.R
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun CollectionItem(
    modifier: Modifier = Modifier,
    text: String,
    description: String,
    icon: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "collection item icon",
            tint = Theme.color.brand.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )
        }
        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = "arrow right icon",
            tint = Theme.color.shade.tertiary,
            modifier = Modifier.padding(start = 8.dp)
        )


    }

}

@Preview(showBackground = true)
@Composable
fun CollectionItemPreview() {
    CollectionItem(
        text = "Title",
        description = "items number",
        icon = R.drawable.due_tone_folder
    )
}