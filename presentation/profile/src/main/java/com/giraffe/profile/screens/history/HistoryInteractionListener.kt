package com.giraffe.profile.screens.history

interface HistoryInteractionListener {
    fun onDeleteClicked():()->Unit
    fun onCloseClicked()

}