package com.deviar.petask.common.di

import android.content.Context
import androidx.room.Room
import com.deviar.petask.common.database.data.PetaskDataBase
import com.deviar.petask.common.database.domain.dao.PetDao
import com.deviar.petask.common.database.domain.dao.TaskDao
import com.deviar.petask.common.database.domain.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PetaskDataBase{
        return Room.databaseBuilder(
            context,
            PetaskDataBase::class.java,
            "petask_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: PetaskDataBase): UserDao {
        return database.userDao
    }

    @Provides
    fun providePetDao(database: PetaskDataBase): PetDao {
        return database.petDao
    }

    @Provides
    fun provideTaskDao(database: PetaskDataBase): TaskDao {
        return database.taskDao
    }
}
