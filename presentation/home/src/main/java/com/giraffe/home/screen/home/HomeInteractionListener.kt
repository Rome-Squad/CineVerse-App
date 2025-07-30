package com.giraffe.home.screen.home

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun loadHomeContent()
    fun onSeeAllRecentlyReleasedClicked(sectionTitle: String, sectionType: String)
    fun onSeeAllTopRatedClicked(sectionTitle: String, sectionType: String)
    fun onSeeAllUpcomingClicked(sectionTitle: String, sectionType: String)
    fun onSeeAllRecentlyViewedClicked(sectionTitle: String, sectionType: String)
    fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: String)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
}