package com.devm7mdibrahim.data.datasource

import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse

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