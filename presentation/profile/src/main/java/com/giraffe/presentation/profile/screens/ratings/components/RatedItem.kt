package com.giraffe.presentation.profile.screens.ratings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.presentation.profile.components.DeleteButton
import com.giraffe.presentation.profile.components.SwipableItem
import com.giraffe.presentation.profile.model.RatedPoster

@Composable
fun RatedItem(
    modifier: Modifier = Modifier,
    ratedPoster: RatedPoster,
    onItemClick: (RatedPoster) -> Unit = {},
    onDeleteClick: (RatedPoster) -> Unit = {}
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RateSection(
            modifier = Modifier.fillMaxWidth(),
            rate = ratedPoster.rating.toDouble(),
        )
        SwipableItem(
            actionButton = {
                DeleteButton(
                    onDeleteClick = { onDeleteClick(ratedPoster) }
                )
            },
        ) {
            PosterItemHorizontal(
                modifier = modifier.fillMaxWidth(),
                movie = ratedPoster.poster,
                onClickPoster = { onItemClick(ratedPoster) }
            )
        }
    }
}