package com.bashar.mychatapp.src.di.hilt.mudules

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bashar.mychatapp.src.data.local.LocalDataSource
import com.bashar.mychatapp.src.data.local.datasources.room.AppDatabase
import com.bashar.mychatapp.src.data.local.datasources.room.dao.TableDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext application: Context): AppDatabase {
        Log.d(
            AppDatabase::class.java.simpleName,
            "From AppModule: Creating a Singleton database instance"
        )
        return Room.databaseBuilder(
            application, AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ) //.addCallback(sRoomDatabaseCallback)
            .build()
    }


    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao,): LocalDataSource =
        LocalDataSource(userDao)

}