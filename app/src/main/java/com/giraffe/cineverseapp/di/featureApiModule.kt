package com.giraffe.cineverseapp.di

import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.home.HomeApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.match.MatchApi
import com.giraffe.match.navigation.MatchApiImp
import com.giraffe.presentation.authentication.nav.AuthenticationApiImp
import com.giraffe.presentation.details.navigation.api.DetailsApiImp
import com.giraffe.presentation.explore.navigation.ExploreApiImp
import com.giraffe.presentation.home.navigation.api.HomeApiImp
import com.giraffe.presentation.profile.navigation.ProfileApiImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeatureApiBindModule {

    @Binds
    @Singleton
    abstract fun bindDetailsApi(impl: DetailsApiImp): DetailsApi

    @Binds
    @Singleton
    abstract fun bindExploreApi(impl: ExploreApiImp): ExploreApi

    @Binds
    @Singleton
    abstract fun bindHomeApi(impl: HomeApiImp): HomeApi

    @Binds
    @Singleton
    abstract fun bindAuthenticationApi(impl: AuthenticationApiImp): AuthenticationApi

    @Binds
    @Singleton
    abstract fun bindProfileApi(impl: ProfileApiImp): ProfileApi

    @Binds
    @Singleton
    abstract fun bindMatchApi(impl: MatchApiImp): MatchApi
}
