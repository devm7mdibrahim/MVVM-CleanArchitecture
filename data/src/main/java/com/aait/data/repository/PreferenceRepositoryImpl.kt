package com.aait.data.repository

import com.aait.data.datasource.PreferenceDataSource
import com.aait.data.local.utils.PreferenceConstants
import com.aait.data.remote.utils.NetworkConstants.Languages.ARABIC
import com.aait.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(private val preferenceDataSource: PreferenceDataSource) :
    PreferenceRepository {

    override suspend fun getLanguage(): Flow<String> = flow {
        preferenceDataSource.getValue(PreferenceConstants.LANGUAGE, ARABIC).collect {
            emit(it as String)
        }
    }

    override suspend fun setLanguage(lang: String) {
        return preferenceDataSource.setValue(PreferenceConstants.LANGUAGE, lang)
    }

    override suspend fun getIsArabicLanguage(): Flow<Boolean> = flow {
        preferenceDataSource.getValue(PreferenceConstants.IS_ARABIC, true).collect {
            emit(it as Boolean)
        }
    }

    override suspend fun getToken(): Flow<String> = flow {
        preferenceDataSource.getValue(PreferenceConstants.TOKEN, "").collect {
            emit(it as String)
        }
    }

    override suspend fun setToken(mToken: String) {
        preferenceDataSource.setValue(PreferenceConstants.TOKEN, mToken)
    }

    override suspend fun getUserData(): Flow<String> = flow {
        preferenceDataSource.getValue(PreferenceConstants.USER_DATA, "").collect {
            emit(it as String)
        }
    }

    override suspend fun setUserData(userData: String) {
        preferenceDataSource.setValue(PreferenceConstants.USER_DATA, userData)
    }

    override suspend fun getFirebaseToken(): Flow<String> = flow {
        preferenceDataSource.getValue(PreferenceConstants.FIREBASE_TOKEN, "abcdef").collect {
            emit(it as String)
        }
    }

    override suspend fun setFirebaseToken(token: String) {
        preferenceDataSource.setValue(PreferenceConstants.FIREBASE_TOKEN, token)
    }

    override suspend fun getAddress(): Flow<String> = flow {
        preferenceDataSource.getValue(PreferenceConstants.ADDRESS, "").collect {
            emit(it as String)
        }
    }

    override suspend fun setAddress(address: String) {
        preferenceDataSource.setValue(PreferenceConstants.ADDRESS, address)
    }

    override suspend fun getLatitude(): Flow<Double> = flow {
        preferenceDataSource.getValue(PreferenceConstants.LATITUDE, 0.0).collect {
            emit(it as Double)
        }
    }

    override suspend fun setLatitude(latitude: Double) {
        preferenceDataSource.setValue(PreferenceConstants.LATITUDE, latitude)
    }

    override suspend fun getLongitude(): Flow<Double> = flow {
        preferenceDataSource.getValue(PreferenceConstants.LONGITUDE, 0.0).collect {
            emit(it as Double)
        }
    }

    override suspend fun setLongitude(longitude: Double) {
        preferenceDataSource.setValue(PreferenceConstants.LONGITUDE, longitude)
    }

    override suspend fun getIsActivated(): Flow<Boolean> = flow {
        preferenceDataSource.getValue(PreferenceConstants.IS_ACTIVATED, false).collect {
            emit(it as Boolean)
        }
    }

    override suspend fun setIsActivated(active: Boolean) {
        preferenceDataSource.setValue(PreferenceConstants.IS_ACTIVATED, active)
    }

    override suspend fun getIsProfileCompleted(): Flow<Boolean> = flow {
        preferenceDataSource.getValue(PreferenceConstants.IS_PROFILE_COMPLETED, false).collect {
            emit(it as Boolean)
        }
    }

    override suspend fun setIsProfileCompleted(completed: Boolean) {
        preferenceDataSource.setValue(PreferenceConstants.IS_PROFILE_COMPLETED, completed)
    }

    override suspend fun getIsFirstTime(): Flow<Boolean> = flow {
        preferenceDataSource.getValue(PreferenceConstants.IS_FIRST_TIME, true).collect {
            emit(it as Boolean)
        }
    }

    override suspend fun setIsFirstTime(firstTime: Boolean) {
        preferenceDataSource.setValue(PreferenceConstants.IS_FIRST_TIME, firstTime)
    }

    override suspend fun onLogout() {
        setAddress("")
        setLatitude(0.0)
        setLongitude(0.0)
        setIsActivated(false)
        setToken("")
        setUserData("")
    }
}