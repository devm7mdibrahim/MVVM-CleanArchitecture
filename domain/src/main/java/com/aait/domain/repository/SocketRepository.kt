package com.aait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface SocketRepository {

    suspend fun addUser(key: String, jsonObject: JSONObject)
    suspend fun addTracker(jsonObject: JSONObject)
    suspend fun exitChat(key: String, jsonObject: JSONObject)
    suspend fun sendMessage(key: String, jsonObject: JSONObject)
    suspend fun updateLocation(key: String, jsonObject: JSONObject)

    fun onMessageReceived(key: String, resultChanel: (JSONObject) -> Unit)
    fun onLocationUpdated(key: String, resultChanel: (JSONObject) -> Unit)

    fun connectSocket(): Flow<Boolean>
    fun disconnectSocket()
}