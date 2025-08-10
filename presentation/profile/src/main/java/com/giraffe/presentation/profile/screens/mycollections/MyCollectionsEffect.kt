package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.media.collections.entity.Collection

sealed class MyCollectionsEffect {
    class NavigateToCollection(val collection: Collection) : MyCollectionsEffect()
    object NavigateToExplore : MyCollectionsEffect()
    object NavigateBack : MyCollectionsEffect()
    class ShowError(val error: Throwable) : MyCollectionsEffect()
}