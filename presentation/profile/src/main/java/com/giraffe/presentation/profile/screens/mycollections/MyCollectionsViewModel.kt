package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.AddCollectionUseCase
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.model.CollectionUi
import com.giraffe.presentation.profile.utils.toEntity
import com.giraffe.presentation.profile.utils.toUi
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCollectionsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase,
) : BaseViewModel<MyCollectionsScreenState, MyCollectionsEffect>(
    MyCollectionsScreenState()
), MyCollectionsInteractionListener {

    init {
        checkLoginStatus()
    }

    private fun getCollections() {
        safeCollect(
            onEmitNewValue = ::onGetCollectionsSuccess,
            onError = ::onFailure,
            block = getCollectionsUseCase::invoke
        )
    }

    private fun onGetCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                collections = collections.map { collection -> collection.toUi() }
            )
        }
    }

    override fun onCollectionClick(collection: CollectionUi) {
        sendEffect(MyCollectionsEffect.NavigateToCollection(collection.toEntity()))
    }

    override fun onNewCollectionNameChange(newCollectionName: String) {
        updateState { it.copy(newCollectionName = newCollectionName) }
    }

    override fun onCreateNewCollectionClick() {
        updateState { it.copy(isBottomSheetVisible = true) }
    }

    override fun onConfirmCreateNewCollectionClick() {
        updateState { it.copy(isLoading = true, isBottomSheetVisible = false) }
        safeExecute(
            onSuccess = { onCreateCollectionSuccess() },
            onError = ::onFailure
        ) {
            addCollectionUseCase(
                CollectionUi(name = state.value.newCollectionName).toEntity()
            )
        }
    }

    private fun onCreateCollectionSuccess() {
        updateState { it.copy(isLoading = false, isNoInternet = false) }
        getCollections()
    }

    private fun onFailure(error: Throwable, isNoInternet: Boolean) {
        updateState { it.copy(isLoading = false, isNoInternet = isNoInternet) }
        sendEffect(MyCollectionsEffect.ShowError(error))
    }

    override fun onDismissBottomSheet() {
        updateState { it.copy(isBottomSheetVisible = false) }
    }

    override fun onStartCollectingClick() {
        sendEffect(MyCollectionsEffect.NavigateToExplore)
    }

    override fun onBackClick() {
        sendEffect(MyCollectionsEffect.NavigateBack)
    }

    private fun checkLoginStatus() {
        safeExecute(
            onSuccess = ::handleLoginSuccess,
            onError = ::onFailure
        ) {
            isLoggedInByAccountUseCase()
        }
    }

    private fun handleLoginSuccess(loggedIn: Boolean) {
        updateState { it.copy(isLoggedIn = loggedIn) }
        if (loggedIn) {
            getCollections()
        } else {
            updateState { it.copy(isLoading = false) }
        }
    }
}
