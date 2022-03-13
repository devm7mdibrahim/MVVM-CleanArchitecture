package com.aait.data.remote.endpoints

import com.aait.data.remote.utils.NetworkConstants.EndPoints.CONVERSATION
import com.aait.data.remote.utils.NetworkConstants.EndPoints.ROOMS
import com.aait.data.remote.utils.NetworkConstants.EndPoints.ROOM_ID
import com.aait.domain.entities.BaseResponse
import com.aait.domain.entities.ChatResponse
import com.aait.domain.entities.RoomsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatEndPoints {
    @GET(ROOMS)
    suspend fun getRooms(): BaseResponse<RoomsResponse>

    @FormUrlEncoded
    @POST(CONVERSATION)
    suspend fun getChatMessages(@Field(ROOM_ID) roomId: Int): BaseResponse<ChatResponse>
}