package com.giraffe.profile.screens.history

import androidx.annotation.StringRes

sealed class HistoryEffect{
    data class ShowError(@StringRes val messageResId: Int) : HistoryEffect()
    data class navigateToHomeScreen(val id:Int): HistoryEffect()

}