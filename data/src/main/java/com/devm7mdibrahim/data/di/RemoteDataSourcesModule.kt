package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.datasource.AuthDataSource
import com.devm7mdibrahim.data.datasource.ChatDataSource
import com.devm7mdibrahim.data.datasource.HomeDataSource
import com.devm7mdibrahim.data.remote.datasource.AuthDataSourceImpl
import com.devm7mdibrahim.data.remote.datasource.ChatDataSourceImpl
import com.devm7mdibrahim.data.remote.datasource.HomeDataSourceImpl
import com.devm7mdibrahim.data.remote.endpoints.AuthEndPoints
import com.devm7mdibrahim.data.remote.endpoints.ChatEndPoints
import com.devm7mdibrahim.data.remote.endpoints.HomeEndPoints
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