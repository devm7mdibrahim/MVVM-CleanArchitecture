package com.devm7mdibrahim.data.datasource

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface SocketDataSource {
    fun connectSocket(): Flow<Boolean>
    fun disconnectSocket()
    fun openChannel(channel: String): Flow<List<String>>
    fun setEmit(emitType: String, jsonObject: JSONObject?)
}