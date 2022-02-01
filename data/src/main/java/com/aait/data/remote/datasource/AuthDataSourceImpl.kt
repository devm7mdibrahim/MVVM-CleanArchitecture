package com.aait.data.remote.datasource

import com.aait.data.datasource.AuthDataSource
import com.aait.data.remote.endpoints.AuthEndPoints
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.AVATAR
import com.aait.data.utils.MultiPartUtil.prepareImagePart
import com.aait.data.utils.MultiPartUtil.toMultiPart
import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(private val authEndPoints: AuthEndPoints): AuthDataSource {

    override suspend fun login(
        phone: String,
        password: String,
        deviceId: String
    ): BaseResponse<AuthData> {
        return authEndPoints.login(
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
    ): BaseResponse<AuthData> {
        return authEndPoints.register(
            name = name.toMultiPart(),
            phone = phone.toMultiPart(),
            email = email.toMultiPart(),
            password = password.toMultiPart(),
            avatar = avatar?.let { prepareImagePart(AVATAR, it) }
        )
    }

}