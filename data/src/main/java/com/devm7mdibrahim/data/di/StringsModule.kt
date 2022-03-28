package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.BuildConfig
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.API_VERSION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object StringsModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteBaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SocketPort

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SocketBaseUrl

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @RemoteBaseUrl
    fun provideRemoteBaseUrl(@BaseUrl baseUrl: String): String = "$baseUrl${API_VERSION}"

    @Provides
    @SocketPort
    fun provideSocketPort(): String = BuildConfig.SOCKET_PORT

    @Provides
    @SocketBaseUrl
    fun provideSocketBaseUrl(@BaseUrl baseUrl: String, @SocketPort socketPort: String): String =
        "${baseUrl}:${socketPort}"
}