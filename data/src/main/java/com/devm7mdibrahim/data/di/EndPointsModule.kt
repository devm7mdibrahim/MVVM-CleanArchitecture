package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.remote.endpoints.AuthEndPoints
import com.devm7mdibrahim.data.remote.endpoints.ChatEndPoints
import com.devm7mdibrahim.data.remote.endpoints.HomeEndPoints
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