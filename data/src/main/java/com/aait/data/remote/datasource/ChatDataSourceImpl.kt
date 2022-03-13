package com.aait.data.remote.datasource

import com.aait.data.datasource.ChatDataSource
import com.aait.data.remote.endpoints.ChatEndPoints
import com.aait.domain.entities.*
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(private val chatEndPoints: ChatEndPoints) :
    ChatDataSource {

    override suspend fun getRooms(): BaseResponse<RoomsResponse> {
        return chatEndPoints.getRooms()
    }

    override suspend fun getChatMessages(roomId: Int): BaseResponse<ChatResponse> {
        return chatEndPoints.getChatMessages(roomId)
    }
}