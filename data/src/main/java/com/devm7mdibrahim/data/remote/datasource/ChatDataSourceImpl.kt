package com.devm7mdibrahim.data.remote.datasource

import com.devm7mdibrahim.data.datasource.ChatDataSource
import com.devm7mdibrahim.data.remote.endpoints.ChatEndPoints
import com.devm7mdibrahim.domain.entities.*
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