package com.giraffe.user

import com.giraffe.repository.datasource.local.OnboardingLocalDataSource
import com.giraffe.user.datastore.OnboardingDatastore
import com.giraffe.user.util.safeCall
import javax.inject.Inject

class OnboardingLocalDataSourceImpl @Inject constructor(
    private val onboardingDatastore: OnboardingDatastore
) : OnboardingLocalDataSource {

    override suspend fun setOnBoardingFirstTime() =
        safeCall { onboardingDatastore.setFirstTimeStatus() }

    override suspend fun isOnBoardingFirstTime(): Boolean =
        safeCall { onboardingDatastore.isFirstTime() }
}