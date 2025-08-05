package com.giraffe.profile.screens.collections.mycollections

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.AddCollectionUseCase
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.collections.usecase.RemoveCollectionUseCase
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.model.CollectionUi
import com.giraffe.profile.model.toEntity
import com.giraffe.profile.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyCollectionsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    private val removeCollectionUseCase: RemoveCollectionUseCase
) : BaseViewModel<MyCollectionsScreenState, MyCollectionsEffect>(
    MyCollectionsScreenState()
), MyCollectionsInteractionListener {

    init {
        getCollections()
    }

    private fun getCollections() {
        safeExecute(
            onError = ::onGetCollectionsFailure,
            onSuccess = ::onGetCollectionsSuccess
        ) {
            getCollectionsUseCase()
        }
    }

    private fun onGetCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                isLoading = false,
                collections = collections.map { collection ->
                    collection.toUi()
                }
            )
        }
    }

    private fun onGetCollectionsFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(MyCollectionsEffect.ShowError(mapErrorToResource(error)))
    }


    override fun onCollectionClick(collection: CollectionUi) {
        sendEffect(MyCollectionsEffect.NavigateToCollection(collection.toEntity()))
    }

    override fun onNewCollectionNameChange(newCollectionName: String) {
        updateState {
            it.copy(
                newCollectionName = newCollectionName
            )
        }
    }

    override fun onCreateNewCollectionClick() {
        updateState {
            it.copy(
                isCreateNewCollectionBottomSheetVisible = true
            )
        }
    }

    override fun onConfirmCreateNewCollectionClick() {
        updateState {
            it.copy(
                isLoading = true,
                isCreateNewCollectionBottomSheetVisible = false
            )
        }

        safeExecute(
            onSuccess = ::onCreateCollectionSuccess,
            onError = ::onCreateCollectionFailure
        ) {
            addCollectionUseCase(
                collection = CollectionUi(
                    name = state.value.newCollectionName
                ).toEntity()
            )
        }
    }

    private fun onCreateCollectionSuccess(result: Unit) {
        updateState {
            it.copy(
                isLoading = false
            )
        }

        getCollections()
    }

    private fun onCreateCollectionFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(MyCollectionsEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onDismissCreateNewCollectionBottomSheet() {
        updateState {
            it.copy(
                isCreateNewCollectionBottomSheetVisible = false
            )
        }
    }

    override fun onStartCollectingClick() {
        sendEffect(MyCollectionsEffect.NavigateToExplore)
    }

    override fun onBackClick() {
        sendEffect(MyCollectionsEffect.NavigateBack)
    }
}