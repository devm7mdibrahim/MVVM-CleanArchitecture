package com.aait.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.aait.data.datasource.PreferenceDataSource
import com.aait.data.local.datasource.PreferenceDataSourceImpl
import com.aait.data.repository.PreferenceRepositoryImpl
import com.aait.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    private const val PREFERENCE_NAME = "base_preferences"

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(PREFERENCE_NAME)
            }
        )

    @Provides
    @Singleton
    fun providePreferencesDataSource(dataStore: DataStore<Preferences>): PreferenceDataSource =
        PreferenceDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesDataSource: PreferenceDataSource): PreferenceRepository =
        PreferenceRepositoryImpl(preferencesDataSource)
}