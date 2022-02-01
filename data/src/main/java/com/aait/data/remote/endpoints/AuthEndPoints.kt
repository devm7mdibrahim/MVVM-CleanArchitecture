package com.aait.data.remote.endpoints

import com.aait.data.remote.utils.NetworkConstants.EndPoints.LOGIN
import com.aait.data.remote.utils.NetworkConstants.EndPoints.REGISTER
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.DEVICE_ID
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.DEVICE_TYPE
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.EMAIL
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.NAME
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.PASSWORD
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.PHONE
import com.aait.data.remote.utils.NetworkConstants.NetworkParams.TYPE_ANDROID
import com.aait.domain.entities.AuthData
import com.aait.domain.entities.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthEndPoints {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(PHONE) phone: String,
        @Field(PASSWORD) password: String,
        @Field(DEVICE_ID) deviceId: String,
        @Field(DEVICE_TYPE) deviceType: String = TYPE_ANDROID
    ): BaseResponse<AuthData>

    @Multipart
    @POST(REGISTER)
    suspend fun register(
        @Part(NAME) name: RequestBody,
        @Part(PHONE) phone: RequestBody,
        @Part(EMAIL) email: RequestBody,
        @Part(PASSWORD) password: RequestBody,
        @Part avatar: MultipartBody.Part?,
    ): BaseResponse<AuthData>
}