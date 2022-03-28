package com.devm7mdibrahim.data.remote.datasource

import com.devm7mdibrahim.data.datasource.AuthDataSource
import com.devm7mdibrahim.data.remote.endpoints.AuthEndPoints
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.NetworkParams.AVATAR
import com.devm7mdibrahim.data.utils.MultiPartUtil.prepareImagePart
import com.devm7mdibrahim.data.utils.MultiPartUtil.toMultiPart
import com.devm7mdibrahim.domain.entities.AuthData
import com.devm7mdibrahim.domain.entities.BaseResponse
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