package com.deviar.petask.common.di

import com.deviar.petask.auth.data.AuthRepository
import com.deviar.petask.common.database.data.PetRepository
import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.domain.dao.PetDao
import com.deviar.petask.common.database.domain.dao.UserDao
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
        auth: FirebaseAuth
    ): UserRepository = UserRepository(userDao, auth)

    fun providePetRepository(
        petDao: PetDao,
        auth: FirebaseAuth
    ): PetRepository = PetRepository(petDao, auth)

    // todos los repositorios van acá
    @Provides
    fun providesAuthRepository(): AuthRepository = AuthRepository()
}