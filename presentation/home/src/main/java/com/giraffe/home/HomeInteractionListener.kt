package com.giraffe.home

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun onCollectionClicked(collectionId: Int, type: CollectionClickType)
    fun onSeeAllPopularClicked()
    fun onSeeAllRecentlyReleasedClicked()
    fun onSeeAllTopRatedClicked()
    fun onSeeAllUpcomingClicked()
    fun onSeeAllRecentlyViewedClicked()
    fun onSeeAllYourCollection()
    fun onWhatShouldIWatchClicked()
    fun onNeedMoreToWatchClicked()
}