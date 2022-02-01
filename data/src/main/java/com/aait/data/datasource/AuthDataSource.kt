package com.aait.data.datasource

import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse

interface AuthDataSource {

    suspend fun login(
        phone: String,
        password: String,
        deviceId: String
    ): BaseResponse<AuthData>

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        avatar: String?,
    ): BaseResponse<AuthData>
}