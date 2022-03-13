package com.aait.data.di

import com.aait.data.datasource.SocketDataSource
import com.aait.data.socket.datasource.SocketDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocketDataSource(socket: Socket): SocketDataSource {
        return SocketDataSourceImpl(socket)
    }

    @Provides
    @Singleton
    fun provideSocket(@StringsModule.SocketBaseUrl socketBaseUrl: String): Socket {
        return IO.socket(socketBaseUrl)
    }
}