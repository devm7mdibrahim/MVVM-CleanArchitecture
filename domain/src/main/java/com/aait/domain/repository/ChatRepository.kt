package com.aait.domain.repository

import com.aait.domain.entities.BaseResponse
import com.aait.domain.entities.ChatResponse
import com.aait.domain.entities.RoomsResponse
import com.aait.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ChatRepository {

    suspend fun getRooms(): Flow<DataState<BaseResponse<RoomsResponse>>>
    suspend fun getChatMessages(roomId: Int): Flow<DataState<BaseResponse<ChatResponse>>>

    fun connectSocket(): Flow<Boolean>
    fun disconnectSocket()

    suspend fun addUser(key: String, jsonObject: JSONObject)
    suspend fun exitChat(key: String, jsonObject: JSONObject)

    suspend fun sendMessage(key: String, jsonObject: JSONObject)
    suspend fun onMessageReceived(key: String): Flow<List<String>>
}