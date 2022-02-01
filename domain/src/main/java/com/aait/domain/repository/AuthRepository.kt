package com.aait.domain.repository

import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import com.aait.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(
        phone: String,
        password: String,
        deviceId: String
    ): Flow<DataState<BaseResponse<AuthData>>>

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String?
    ): Flow<DataState<BaseResponse<AuthData>>>
}