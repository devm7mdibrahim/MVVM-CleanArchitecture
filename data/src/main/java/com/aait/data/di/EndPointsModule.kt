package com.aait.data.di

import com.aait.data.remote.endpoints.AuthEndPoints
import com.aait.data.remote.endpoints.ChatEndPoints
import com.aait.data.remote.endpoints.HomeEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EndPointsModule {

    @Provides
    @Singleton
    fun provideAuthEndPoints(retrofit: Retrofit): AuthEndPoints =
        retrofit.create(AuthEndPoints::class.java)

    @Provides
    @Singleton
    fun provideHomeEndPoints(retrofit: Retrofit): HomeEndPoints =
        retrofit.create(HomeEndPoints::class.java)

    @Provides
    @Singleton
    fun provideChatEndPoints(retrofit: Retrofit): ChatEndPoints =
        retrofit.create(ChatEndPoints::class.java)
}