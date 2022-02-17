package com.aait.coredata.socket.datasource

import androidx.lifecycle.MutableLiveData
import com.aait.data.datasource.SocketDataSource
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class SocketDataSourceImpl @Inject constructor(
    private val socket: Socket
) : SocketDataSource {

    private val connectLiveData = MutableLiveData<Boolean>()

    override fun connectToSocket(): Flow<Boolean> = flow {
        if (!socket.connected()) {
            socket.connect()
            onConnectSocket()
        } else {
            emit(true)
        }
    }

    override fun disconnectSocket() {
        if (socket.connected()) {
            socket.disconnect()
        }
    }

    override fun openChannel(
        channel: String,
        socketReConnect: Int,
        resultChanel: (JSONObject) -> Unit
    ) {
//        socket.off()
        socket.on(channel) {
            resultChanel(JSONObject(it[0].toString()))
        }
    }

    override fun setEmit(
        emitType: String,
        jsonObject: JSONObject?,
        socketReConnect: Int
    ) {
        if (socket.connected()) {
            socket.emit(emitType, jsonObject)
        } else {
            connectToSocket()
            if (socketReConnect <= 3) {
                setEmit(emitType, jsonObject, socketReConnect + 1)
            }
        }
    }

    override fun onConnectSocket() {
        socket.on(Socket.EVENT_CONNECT) {
            connectLiveData.postValue(true)
            socket.off(Socket.EVENT_CONNECT)
        }

//        socket.on(Socket.EVENT_CONNECT_TIMEOUT) {
//            onReconnectSocket()
//        }
//        socket.on(Socket.EVENT_RECONNECT_FAILED) {
//            onReconnectSocket()
//        }
//        socket.on(Socket.EVENT_ERROR) {
//            onReconnectSocket()
//        }
    }

    override fun onDisconnectSocket() {
        connectLiveData.postValue(true)
        socket.disconnect()
    }

    override fun onReconnectSocket() {
//        socket.on(Socket.EVENT_RECONNECT) {
//            connectLiveData.postValue(true)
//        }
    }
}