package com.deviar.petask.auth.di

import com.deviar.petask.auth.data.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun providesAuthRepository(): AuthRepository = AuthRepository()
}