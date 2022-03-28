package com.devm7mdibrahim.data.di

import com.devm7mdibrahim.data.BuildConfig
import com.devm7mdibrahim.data.di.StringsModule.RemoteBaseUrl
import com.devm7mdibrahim.data.remote.utils.NetworkConstants
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.NETWORK_TIMEOUT
import com.devm7mdibrahim.domain.repository.PreferenceRepository
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun moshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
        preferenceRepository: PreferenceRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .header(
                        NetworkConstants.LANGUAGE,
                        getLanguage(preferenceRepository)
                    )
                    .header(
                        NetworkConstants.AUTHORIZATION,
                        "${NetworkConstants.BEARER}${getToken(preferenceRepository)}"
                    )

                val request = requestBuilder.build()
                return@addInterceptor chain.proceed(request)
            }.connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    private fun getLanguage(preferenceRepository: PreferenceRepository): String {
        var language: String
        runBlocking(Dispatchers.IO) {
            language = preferenceRepository.getLanguage().first()
        }
        return language
    }

    private fun getToken(preferenceRepository: PreferenceRepository): String {
        var token: String
        runBlocking(Dispatchers.IO) {
            token = preferenceRepository.getToken().first()
        }
        return token
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @RemoteBaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO
}
