package com.deviar.petask.auth.di

import com.deviar.petask.auth.data.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        loginRepositoryImpl: AuthRepository
    ): AuthRepository
}