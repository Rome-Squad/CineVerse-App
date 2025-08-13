package com.giraffe.cineverseapp.di

import com.giraffe.repository.encryption.EncryptionService
import com.giraffe.repository.encryption.KeyStoreService
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.encryption.IKeyStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EncryptionModule {

    companion object {
        @Provides
        @Singleton
        fun provideKeyStoreService(): IKeyStoreService =
            KeyStoreService()

        @Provides
        @Singleton
        fun provideEncryptionService(
            keyStoreService: IKeyStoreService
        ): IEncryptionService =
            EncryptionService(keyStoreService)
    }
}