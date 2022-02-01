package com.aait.data.repository

import com.aait.data.datasource.AuthDataSource
import com.aait.data.remote.utils.safeApiCall
import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import com.aait.domain.repository.AuthRepository
import com.aait.domain.util.DataState
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
    ): Flow<DataState<BaseResponse<AuthData>>> = flow {
        authDataSource.register(
            name = name,
            phone = phone,
            email = email,
            password = password,
            avatar = avatar
        )
    }
}