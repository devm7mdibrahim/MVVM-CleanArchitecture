package com.aait.sa.ui.cycles.chatting_cycle.conversations

import androidx.lifecycle.viewModelScope
import com.aait.domain.entities.BaseResponse
import com.aait.domain.entities.RoomsResponse
import com.aait.domain.repository.ChatRepository
import com.aait.domain.util.DataState
import com.aait.sa.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(private val chatRepository: ChatRepository) :
    BaseViewModel() {

    private val _roomsResponse =
        MutableStateFlow<DataState<BaseResponse<RoomsResponse>>>(DataState.Idle)
    val roomsResponse: MutableStateFlow<DataState<BaseResponse<RoomsResponse>>>
        get() = _roomsResponse

    fun getRooms() {
        viewModelScope.launch {
            chatRepository.getRooms().onEach {
                _roomsResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}