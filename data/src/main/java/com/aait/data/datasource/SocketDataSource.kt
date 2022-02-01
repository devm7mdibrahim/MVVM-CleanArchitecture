package com.aait.data.datasource

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface SocketDataSource {

    fun connectToSocket(): Flow<Boolean>
    fun disconnectSocket()
    suspend fun openChannel(channel: String, socketReConnect: Int): Flow<JSONObject>
    fun setEmit(emitType: String, jsonObject: JSONObject?, socketReConnect: Int)
    fun onConnectSocket()
    fun onDisconnectSocket()
    fun onReconnectSocket()
}