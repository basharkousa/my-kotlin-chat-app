package com.bashar.mychatapp.src.di.hilt.mudules

import com.bashar.mychatapp.src.data.remote.RemoteDataSource
import com.bashar.mychatapp.src.data.remote.api.AppApi
import com.bashar.mychatapp.src.data.remote.constants.ConstantsRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(ConstantsRemote.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(ConstantsRemote.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(ConstantsRemote.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
//        okHttpClient.addInterceptor(RequestInterceptor())
        okHttpClient.addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val url = originalHttpUrl.newBuilder()
//                .addQueryParameter("api_key", Constants.API_KEY)
//                .addQueryParameter("format", "json")
                .build()
            val request = originalRequest.newBuilder().url(url).build()
            chain.proceed(request)
        }
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantsRemote.BASE_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideAppApi(retrofit: Retrofit): AppApi = retrofit.create(AppApi::class.java)

    fun provideRemoteDataSource(appApi: AppApi): RemoteDataSource =
        RemoteDataSource(appApi)
}