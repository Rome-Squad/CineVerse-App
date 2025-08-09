package com.giraffe.presentation.profile.screens.collections.mycollections

import androidx.annotation.StringRes
import com.giraffe.media.collections.entity.Collection

sealed class MyCollectionsEffect {

    class NavigateToCollection(val collection: Collection) : MyCollectionsEffect()
    object NavigateToExplore : MyCollectionsEffect()
    class ShowError(@param:StringRes val message: Int) : MyCollectionsEffect()

    object NavigateBack : MyCollectionsEffect()
}