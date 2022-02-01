package com.aait.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun getLanguage(): Flow<String>
    suspend fun setLanguage(lang: String)

    suspend fun getIsArabicLanguage(): Flow<Boolean>

    suspend fun getToken():Flow<String>
    suspend fun setToken(mToken: String)

    suspend fun getUserData(): Flow<String>
    suspend fun setUserData(userData: String)

    suspend fun getFirebaseToken(): Flow<String>
    suspend fun setFirebaseToken(token: String)

    suspend fun getAddress(): Flow<String>
    suspend fun setAddress(address: String)

    suspend fun getLatitude(): Flow<Double>
    suspend fun setLatitude(latitude: Double)

    suspend fun getLongitude(): Flow<Double>
    suspend fun setLongitude(longitude: Double)

    suspend fun getIsActivated(): Flow<Boolean>
    suspend fun setIsActivated(active: Boolean)

    suspend fun getIsProfileCompleted(): Flow<Boolean>
    suspend fun setIsProfileCompleted(completed: Boolean)

    suspend fun getIsFirstTime(): Flow<Boolean>
    suspend fun setIsFirstTime(firstTime: Boolean)

    suspend fun onLogout()
}