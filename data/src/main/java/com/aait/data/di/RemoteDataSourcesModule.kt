package com.aait.data.di

import com.aait.data.datasource.AuthDataSource
import com.aait.data.datasource.HomeDataSource
import com.aait.data.remote.datasource.AuthDataSourceImpl
import com.aait.data.remote.datasource.HomeDataSourceImpl
import com.aait.data.remote.endpoints.AuthEndPoints
import com.aait.data.remote.endpoints.HomeEndPoints
import com.aait.data.repository.AuthRepositoryImpl
import com.aait.data.repository.HomeRepositoryImpl
import com.aait.domain.repository.AuthRepository
import com.aait.domain.repository.HomeRepository
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
}