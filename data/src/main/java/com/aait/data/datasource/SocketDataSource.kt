package com.aait.data.datasource

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface SocketDataSource {

    fun connectToSocket(): Flow<Boolean>
    fun disconnectSocket()
    fun openChannel(channel: String, socketReConnect: Int,resultChanel: (JSONObject) -> Unit)
    fun setEmit(emitType: String, jsonObject: JSONObject?, socketReConnect: Int)
    fun onConnectSocket()
    fun onDisconnectSocket()
    fun onReconnectSocket()
}