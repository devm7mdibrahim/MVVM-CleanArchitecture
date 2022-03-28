package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.datasource.AuthDataSource
import com.devm7mdibrahim.data.datasource.ChatDataSource
import com.devm7mdibrahim.data.datasource.HomeDataSource
import com.devm7mdibrahim.data.datasource.SocketDataSource
import com.devm7mdibrahim.data.repository.AuthRepositoryImpl
import com.devm7mdibrahim.data.repository.ChatRepositoryImpl
import com.devm7mdibrahim.data.repository.HomeRepositoryImpl
import com.devm7mdibrahim.domain.repository.AuthRepository
import com.devm7mdibrahim.domain.repository.ChatRepository
import com.devm7mdibrahim.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository =
        HomeRepositoryImpl(homeDataSource)

    @Provides
    @Singleton
    fun provideChatRepository(chatDataSource: ChatDataSource, socketDataSource: SocketDataSource): ChatRepository =
        ChatRepositoryImpl(chatDataSource, socketDataSource)
}