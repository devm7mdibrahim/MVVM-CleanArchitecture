package com.devm7mdibrahim.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
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

    private const val PREFERENCE_NAME = "app_preferences"

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