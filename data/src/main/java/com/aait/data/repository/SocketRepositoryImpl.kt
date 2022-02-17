package com.aait.data.repository

import com.aait.data.datasource.SocketDataSource
import com.aait.domain.repository.SocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class SocketRepositoryImpl @Inject constructor(private val socketDataSource: SocketDataSource) :
    SocketRepository {

    override suspend fun addUser(key: String, jsonObject: JSONObject) {
        socketDataSource.setEmit(key, jsonObject, 1)
    }

    override suspend fun addTracker(jsonObject: JSONObject) {

    }

    override suspend fun exitChat(key: String, jsonObject: JSONObject) {
        socketDataSource.setEmit(key, jsonObject, 1)
    }

    override suspend fun sendMessage(key: String, jsonObject: JSONObject) {
        socketDataSource.setEmit(key, jsonObject, 1)
    }

    override suspend fun updateLocation(key: String, jsonObject: JSONObject) {
        socketDataSource.setEmit(key, jsonObject, 1)
    }

    override fun onMessageReceived(key: String, resultChanel: (JSONObject) -> Unit) {
        socketDataSource.openChannel(key, 1, resultChanel)
    }

    override fun onLocationUpdated(key: String, resultChanel: (JSONObject) -> Unit) {
        socketDataSource.openChannel(key, 1, resultChanel)
    }

    override fun connectSocket(): Flow<Boolean> = flow {
        emitAll(socketDataSource.connectToSocket())
    }

    override fun disconnectSocket() {
        socketDataSource.disconnectSocket()
    }
}