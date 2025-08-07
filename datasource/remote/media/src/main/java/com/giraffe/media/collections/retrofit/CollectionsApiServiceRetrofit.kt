package com.giraffe.media.collections.retrofit

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.response.CollectionIdResponse
import com.giraffe.media.collections.response.CollectionItemIdRequestBody
import com.giraffe.media.collections.response.CollectionItemsResponse
import com.giraffe.media.collections.response.CollectionsResponse
import com.giraffe.media.collections.response.DefaultResponse
import com.giraffe.media.util.NetworkConstants.ACCOUNT_ID_PATH
import com.giraffe.media.util.NetworkConstants.ADD_MOVIE_TO_COLLECTION
import com.giraffe.media.util.NetworkConstants.CLEAR_COLLECTION
import com.giraffe.media.util.NetworkConstants.COLLECTIONS_END_POINT
import com.giraffe.media.util.NetworkConstants.COLLECTION_END_POINT
import com.giraffe.media.util.NetworkConstants.COLLECTION_ID_PATH
import com.giraffe.media.util.NetworkConstants.CONFIRM_CLEAR_COLLECTION
import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.REMOVE_MOVIE_FROM_COLLECTION
import com.giraffe.media.util.NetworkConstants.USER_END_POINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApiServiceRetrofit {
    @GET("$USER_END_POINT/{$ACCOUNT_ID_PATH}/$COLLECTIONS_END_POINT")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getCollections(
        @Path(ACCOUNT_ID_PATH) accountId: Int
    ): Response<CollectionsResponse>

    @GET("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}")
    suspend fun getCollectionDetails(
        @Path(COLLECTION_ID_PATH) collectionId: Int
    ): Response<CollectionDto>

    @POST(COLLECTION_END_POINT)
    @Headers("$NEEDS_SESSION: true")
    suspend fun addCollection(
        @Body collection: CollectionDto
    ): Response<CollectionIdResponse>

    @DELETE("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}")
    @Headers("$NEEDS_SESSION: true")
    suspend fun removeCollection(
        @Path(COLLECTION_ID_PATH) collectionId: Int
    ): Response<DefaultResponse>

    @POST("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}/$CLEAR_COLLECTION")
    @Headers("$NEEDS_SESSION: true")
    suspend fun clearCollection(
        @Path(COLLECTION_ID_PATH) collectionId: Int,
        @Query(CONFIRM_CLEAR_COLLECTION) confirm: Boolean = true
    ): Response<DefaultResponse>

    @POST("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}/$ADD_MOVIE_TO_COLLECTION")
    @Headers("$NEEDS_SESSION: true")
    suspend fun addMovieToCollection(
        @Path(COLLECTION_ID_PATH) collectionId: Int,
        @Body body: CollectionItemIdRequestBody
    ): Response<DefaultResponse>

    @POST("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}/$REMOVE_MOVIE_FROM_COLLECTION")
    @Headers("$NEEDS_SESSION: true")
    suspend fun removeMovieFromCollection(
        @Path(COLLECTION_ID_PATH) collectionId: Int,
        @Body body: CollectionItemIdRequestBody
    ): Response<DefaultResponse>

    @GET("$COLLECTION_END_POINT/{$COLLECTION_ID_PATH}")
    suspend fun getCollectionItems(
        @Path(COLLECTION_ID_PATH) collectionId: Int
    ): Response<CollectionItemsResponse>
}