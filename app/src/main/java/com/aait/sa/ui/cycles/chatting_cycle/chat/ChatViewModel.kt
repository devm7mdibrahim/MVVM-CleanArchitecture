package com.aait.sa.ui.cycles.chatting_cycle.chat

import androidx.lifecycle.viewModelScope
import com.aait.domain.entities.BaseResponse
import com.aait.domain.entities.ChatResponse
import com.aait.domain.repository.ChatRepository
import com.aait.domain.util.DataState
import com.aait.sa.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository) :
    BaseViewModel() {

    private val _chatResponse =
        MutableStateFlow<DataState<BaseResponse<ChatResponse>>>(DataState.Idle)
    val chatResponse: MutableStateFlow<DataState<BaseResponse<ChatResponse>>>
        get() = _chatResponse

    fun getChatMessages(roomId: Int) {
        viewModelScope.launch {
            chatRepository.getChatMessages(roomId).onEach {
                _chatResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
    fun sendMessage(key: String, jsonObject: JSONObject) {
        viewModelScope.launch {
            chatRepository.sendMessage(key, jsonObject)
        }
    }

    private var _connectToSocketResponse = MutableStateFlow(false)
    val connectToSocketResponse: MutableStateFlow<Boolean>
        get() = _connectToSocketResponse

    fun connectToSocket() {
        viewModelScope.launch {
            chatRepository.connectSocket().onEach {
                _connectToSocketResponse.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun addUser(key: String, jsonObject: JSONObject) {
        viewModelScope.launch {
            chatRepository.addUser(key, jsonObject)
        }
    }

    fun disconnectSocket() {
        viewModelScope.launch {
            chatRepository.disconnectSocket()
        }
    }

    fun exitChat(key: String, jsonObject: JSONObject) {
        viewModelScope.launch {
            chatRepository.exitChat(key, jsonObject)
        }
    }

    private var _newMessageFlow = MutableStateFlow(emptyList<String>())
    val newMessageFlow: MutableStateFlow<List<String>>
        get() = _newMessageFlow

    fun openChannel(
        key: String,
    ) {
        viewModelScope.launch {
            chatRepository.onMessageReceived(
                key,
            ).onEach {
                _newMessageFlow.value = it
            }.launchIn(viewModelScope)
        }
    }
}