package com.bashar.mychatapp.src.di.hilt.mudules

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bashar.mychatapp.src.data.local.LocalDataSource
import com.bashar.mychatapp.src.data.local.datasources.room.AppDatabase
import com.bashar.mychatapp.src.data.local.datasources.room.dao.ChatDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.MessageDao
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
    fun provideChatDao(appDatabase: AppDatabase): ChatDao = appDatabase.chatDao()

    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao,chatDao: ChatDao,messageDao: MessageDao): LocalDataSource =
        LocalDataSource(userDao,chatDao,messageDao)

}