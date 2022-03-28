package com.devm7mdibrahim.data.socket.datasource

import com.devm7mdibrahim.data.datasource.SocketDataSource
import io.socket.client.Socket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

class SocketDataSourceImpl @Inject constructor(
    private val socket: Socket
) : SocketDataSource {

    private val connectFlow = MutableStateFlow(false)
    private val messagesFlow = MutableStateFlow(emptyList<String>())

    override fun connectSocket(): Flow<Boolean> = flow {
        if (!socket.connected()) {
            socket.connect()

            socket.on(Socket.EVENT_CONNECT) {
                GlobalScope.launch { connectFlow.emit(true) }
                socket.off(Socket.EVENT_CONNECT)
            }

            socket.on(Socket.EVENT_CONNECT_ERROR) { connectSocket() }

        } else {
            GlobalScope.launch { connectFlow.emit(true) }
        }

        connectFlow.collect {
            emit(it)
        }
    }

    override fun disconnectSocket() {
        if (socket.connected()) {
            socket.disconnect()
        }

        GlobalScope.launch { messagesFlow.emit(emptyList()) }
    }

    override fun openChannel(
        channel: String
    ): Flow<List<String>> = flow {
        socket.off()
        socket.on(channel) {
            GlobalScope.launch {
                it.map { it.toString() }.let { messages ->
                    messagesFlow.emit(messages)
                }
            }
        }

        messagesFlow.collect {
            emit(it)
        }
    }

    override fun setEmit(
        emitType: String,
        jsonObject: JSONObject?
    ) {
        if (socket.connected()) {
            socket.emit(emitType, jsonObject)
        } else {
            GlobalScope.launch {
                connectSocket().collect {
                    if (it) {
                        socket.emit(emitType, jsonObject)
                    }
                }
            }
        }
    }
}