package com.aait.data.di

import com.aait.data.datasource.AuthDataSource
import com.aait.data.datasource.ChatDataSource
import com.aait.data.datasource.HomeDataSource
import com.aait.data.remote.datasource.AuthDataSourceImpl
import com.aait.data.remote.datasource.ChatDataSourceImpl
import com.aait.data.remote.datasource.HomeDataSourceImpl
import com.aait.data.remote.endpoints.AuthEndPoints
import com.aait.data.remote.endpoints.ChatEndPoints
import com.aait.data.remote.endpoints.HomeEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourcesModule {

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authEndPoints: AuthEndPoints): AuthDataSource =
        AuthDataSourceImpl(authEndPoints)

    @Provides
    @Singleton
    fun provideHomeRemoteDataSource(homeEndPoints: HomeEndPoints): HomeDataSource =
        HomeDataSourceImpl(homeEndPoints)

    @Provides
    @Singleton
    fun provideChatRemoteDataSource(chatEndPoints: ChatEndPoints): ChatDataSource =
        ChatDataSourceImpl(chatEndPoints)
}