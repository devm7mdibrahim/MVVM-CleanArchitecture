package com.devm7mdibrahim.data.repository

import com.devm7mdibrahim.data.datasource.AuthDataSource
import com.devm7mdibrahim.data.remote.utils.safeApiCall
import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.repository.AuthRepository
import com.devm7mdibrahim.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {
    override suspend fun login(
        phone: String,
        password: String,
        deviceId: String
    ): Flow<DataState<BaseResponse<AuthData>>> = safeApiCall {
        authDataSource.login(
            phone = phone,
            password = password,
            deviceId = deviceId
        )
    }

    override suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String?
    ): Flow<DataState<BaseResponse<AuthData>>> = safeApiCall {
        authDataSource.register(
            name = name,
            phone = phone,
            email = email,
            password = password,
            avatar = avatar
        )
    }
}