package com.aait.data.di

import com.aait.data.datasource.AuthDataSource
import com.aait.data.datasource.ChatDataSource
import com.aait.data.datasource.HomeDataSource
import com.aait.data.datasource.SocketDataSource
import com.aait.data.repository.AuthRepositoryImpl
import com.aait.data.repository.ChatRepositoryImpl
import com.aait.data.repository.HomeRepositoryImpl
import com.aait.domain.repository.AuthRepository
import com.aait.domain.repository.ChatRepository
import com.aait.domain.repository.HomeRepository
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