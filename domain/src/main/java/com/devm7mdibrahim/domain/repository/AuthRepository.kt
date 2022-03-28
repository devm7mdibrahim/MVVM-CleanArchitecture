package com.devm7mdibrahim.domain.repository

import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.util.DataState
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