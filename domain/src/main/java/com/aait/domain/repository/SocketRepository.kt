package com.aait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface SocketRepository {

    suspend fun addUser(key: String, jsonObject: JSONObject)
    suspend fun addTracker(jsonObject: JSONObject)
    suspend fun exitChat(key: String, jsonObject: JSONObject)
    suspend fun sendMessage(key: String, jsonObject: JSONObject)
    suspend fun updateLocation(key: String, jsonObject: JSONObject)

    suspend fun onMessageReceived(key: String): Flow<JSONObject>
    suspend fun onLocationUpdated(key: String): Flow<JSONObject>

    fun connectSocket(): Flow<Boolean>
    fun disconnectSocket()
}