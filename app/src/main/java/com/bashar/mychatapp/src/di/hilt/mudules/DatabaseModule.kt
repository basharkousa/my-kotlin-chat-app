package com.bashar.mychatapp.src.di.hilt.mudules

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.bashar.mychatapp.src.data.local.LocalDataSource
import com.bashar.mychatapp.src.data.local.datasources.dataStore.prefsdataStore.AppPreferences
import com.bashar.mychatapp.src.data.local.datasources.room.AppDatabase
import com.bashar.mychatapp.src.data.local.datasources.room.dao.ChatDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.MessageDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    //For dataStore preferences

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, AppPreferences.APP_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(AppPreferences.APP_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>) = AppPreferences(dataStore)


    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao,chatDao: ChatDao,messageDao: MessageDao,userPreferences: AppPreferences): LocalDataSource =
        LocalDataSource(userDao,chatDao,messageDao,userPreferences)

}