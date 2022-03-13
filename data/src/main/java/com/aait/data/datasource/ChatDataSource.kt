package com.aait.data.datasource

import com.aait.domain.entities.*

interface ChatDataSource {
    suspend fun getRooms(): BaseResponse<RoomsResponse>
    suspend fun getChatMessages(roomId: Int): BaseResponse<ChatResponse>
}