package com.giraffe.repository

import com.giraffe.repository.datasource.local.OnboardingLocalDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val localDataSource: OnboardingLocalDataSource
) : OnboardingRepository {

    override suspend fun setFirstTimeStatus() = SafeCall {
        localDataSource.setOnBoardingFirstTime()
    }

    override suspend fun isFirstTime(): Boolean = SafeCall {
        localDataSource.isOnBoardingFirstTime()
    }
}