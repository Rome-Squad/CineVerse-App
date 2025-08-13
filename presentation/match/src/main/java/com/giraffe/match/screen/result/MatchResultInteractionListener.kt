package com.giraffe.match.screen.result

interface MatchResultInteractionListener {
    fun onAddToCollection(itemId: Int, mediaType: MediaType)
    fun onCollectionBottomSheetDismiss()
    fun onLoginButtonClick()
    fun onBackClick()
    fun onRetryClick()
    fun onItemPosterClick(itemId: Int, mediaType: MediaType)
    fun onPlayButtonClick(youtubeVideoId: String)
    fun onCreateCollectionButtonClick()
    fun onCollectionClick(collectionId: Int)
    fun onNewCollectionNameChange(name: String)
    fun onConfirmCreateNewCollectionClick()
    fun onCancelCreateNewCollectionClick()
    fun onDismissLoginBottomSheet()
}