package com.giraffe.user.repository

interface OnboardingRepository {
    suspend fun setFirstTimeStatus()
    suspend fun isFirstTime(): Boolean
}