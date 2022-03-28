package com.devm7mdibrahim.data.di

import android.content.Context
import com.devm7mdibrahim.data.datasource.PreferenceDataSource
import com.devm7mdibrahim.data.local.datasource.PreferenceDataSourceImpl
import com.devm7mdibrahim.data.repository.PreferenceRepositoryImpl
import com.devm7mdibrahim.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSourceImpl(context)

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesDataSource: PreferenceDataSource): PreferenceRepository =
        PreferenceRepositoryImpl(preferencesDataSource)
}