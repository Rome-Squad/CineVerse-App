package com.giraffe.cineverseapp.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.R


@Composable
fun RatingStatrsItem(
    modifier: Modifier = Modifier,
    rate: Int = 0,
    onRateClickEnabled: Boolean = true,
    onRateClick: (Int) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        for (i in 0 until 5) {

            Icon(
                painter = painterResource(
                    id = if (i < rate)
                        R.drawable.bold_star
                    else
                        R.drawable.outline_star
                ),
                contentDescription = "rate $rate",
                modifier = Modifier
                    .then(
                        if (onRateClickEnabled)
                            Modifier.clickable { onRateClick(i + 1) }
                        else
                            Modifier
                    )

            )

        }

    }

}