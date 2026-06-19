package com.deviar.petask.common.di

import com.deviar.petask.auth.data.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    // todos los repositorios van acá
    @Provides
    fun providesAuthRepository(): AuthRepository = AuthRepository()
}