package com.bashar.mychatapp.src.di.hilt.mudules

import com.bashar.mychatapp.src.data.Repository
import com.bashar.mychatapp.src.data.local.LocalDataSource
import com.bashar.mychatapp.src.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository = Repository(remoteDataSource, localDataSource)


}