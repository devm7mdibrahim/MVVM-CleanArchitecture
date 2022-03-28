package com.devm7mdibrahim.data.datasource

import com.devm7mdibrahim.domain.entities.*

interface ChatDataSource {
    suspend fun getRooms(): BaseResponse<RoomsResponse>
    suspend fun getChatMessages(roomId: Int): BaseResponse<ChatResponse>
}