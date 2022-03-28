package com.devm7mdibrahim.data.remote.endpoints

import com.devm7mdibrahim.data.remote.utils.NetworkConstants.EndPoints.CONVERSATION
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.EndPoints.ROOMS
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.EndPoints.ROOM_ID
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.entities.ChatResponse
import com.devm7mdibrahim.domain.entities.RoomsResponse
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