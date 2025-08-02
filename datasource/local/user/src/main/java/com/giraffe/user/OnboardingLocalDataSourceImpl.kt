package com.giraffe.user

import com.giraffe.repository.datasource.local.OnboardingLocalDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.datastore.OnboardingDatastore
import javax.inject.Inject

class OnboardingLocalDataSourceImpl @Inject constructor(
    private val onboardingDatastore: OnboardingDatastore
) : OnboardingLocalDataSource {

    override suspend fun setOnBoardingFirstTime() = SafeCall {
        onboardingDatastore.setFirstTimeStatus()
    }

    override suspend fun isOnBoardingFirstTime(): Boolean = SafeCall {
        onboardingDatastore.isFirstTime()
    }
}