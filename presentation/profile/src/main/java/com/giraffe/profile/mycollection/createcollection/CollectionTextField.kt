package com.giraffe.profile.mycollection.createcollection

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.Theme

@Composable
fun CollectionTextField(
    leadingIcon: Int, hintText: String, value: String, onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .border(width = 1.dp, color = Theme.color.stroke.primary)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Theme.radius.lg),
        placeholder = {
            Text(
                text = hintText,
                style = Theme.textStyle.body.md.regular,
                color = Theme.color.shade.tertiary,
                modifier = Modifier.padding(top = 14.5.dp, bottom = 14.5.dp)

            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = "create collection icon",
                tint = Theme.color.shade.tertiary,
                modifier = Modifier.padding(
                        start = 16.dp,
                        top = 14.dp,
                        bottom = 14.dp,
                        end = 0.5.dp
                    )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Theme.color.background.card,
            unfocusedContainerColor = Theme.color.background.card,
            focusedIndicatorColor = Theme.color.brand.primary,
            unfocusedIndicatorColor = Theme.color.stroke.primary,
            focusedPlaceholderColor = Theme.color.shade.tertiary,
        )

    )
}

@Preview(showBackground = false)
@Composable
fun CollectionTextFieldPreview() {
    CollectionTextField(
        leadingIcon = R.drawable.outline_folder,
        hintText = "e.g. My Watchlist",
        value = "",
        onValueChange = {})
}