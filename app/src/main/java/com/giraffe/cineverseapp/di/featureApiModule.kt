package com.giraffe.cineverseapp.di

import com.giraffe.authentication.AuthenticationApi
import com.giraffe.authentication.nav.AuthenticationApiImp
import com.giraffe.details.DetailsApi
import com.giraffe.details.nav.DetailsApiImp
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.navigation.ExploreApiImp
import com.giraffe.home.HomeApi
import com.giraffe.home.navigation.HomeApiImp
import com.google.firebase.sessions.dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureApiModule {

    @Provides
    @Singleton
    fun bindAuthenticationApi(exploreApi: ExploreApi): AuthenticationApi =
        AuthenticationApiImp(exploreApi)

    @Provides
    @Singleton
    fun bindDetailsApi(): DetailsApi = DetailsApiImp()

    @Provides
    @Singleton
    fun bindExploreApi(detailsApi: DetailsApi): ExploreApi = ExploreApiImp(detailsApi)

    @Provides
    @Singleton
    fun bindHomeApi(detailsApi: DetailsApi): HomeApi = HomeApiImp(detailsApi)
}
