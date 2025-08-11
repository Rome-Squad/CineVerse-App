package com.giraffe.presentation.profile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileRowItem(
    @param:StringRes val textResId: Int,
    @param:DrawableRes val iconResId: Int,
    val onClick: () -> Unit
)