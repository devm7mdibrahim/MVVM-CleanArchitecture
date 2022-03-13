package com.aait.sa.ui.cycles.chatting_cycle.chat

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aait.domain.entities.MessagesItem
import com.aait.sa.databinding.FragmentChatBinding
import com.aait.sa.ui.base.BaseFragment
import com.aait.sa.ui.utils.Constants.ChatKeys.MESSAGE
import com.aait.sa.ui.utils.Constants.ChatKeys.MESSAGE_TEXT
import com.aait.sa.ui.utils.Constants.ChatKeys.MESSAGE_TYPE
import com.aait.sa.ui.utils.Constants.ChatKeys.RECEIVE_MESSAGE
import com.aait.sa.ui.utils.Constants.ChatKeys.ROOM_ID
import com.aait.sa.ui.utils.Constants.ChatKeys.SEND_MESSAGE
import com.aait.sa.ui.utils.Constants.ChatKeys.SUBSCRIBE_ROOM
import com.aait.sa.ui.utils.Constants.ChatKeys.UNSUBSCRIBE_ROOM
import com.aait.sa.ui.utils.Constants.ChatKeys.USER_ID
import com.aait.sa.ui.utils.applyCommonSideEffects
import com.aait.utils.common.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    override val viewModel by viewModels<ChatViewModel>()
    private val args by navArgs<ChatFragmentArgs>()
    private val chatAdapter by lazy { ChatAdapter() }
    private val messagesList = mutableListOf<MessagesItem>()
    private var userId: Int = 1
    private var receiverId: Int = 1

    override fun startObserver() {
        super.startObserver()
        getChatMessagesObserver()
        connectToSocketListener()
        openChannelObserver()
    }

    override fun afterCreateView() {
        super.afterCreateView()
        initRV()
        getChatMessages()
    }

    override fun handleClicks() {
        super.handleClicks()
        binding.ivSend.setOnClickListener {
            binding.etMessage.fetchText().takeIf { it.isNotEmpty() }?.let {
                sendMessage(it)
            }
        }
    }

    private fun sendMessage(message: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.getUserData().first {
                val jsonObject = JSONObject()
                jsonObject.put(USER_ID, receiverId)
                jsonObject.put(MESSAGE, message)
                jsonObject.put(MESSAGE_TYPE, MESSAGE_TEXT)
                viewModel.sendMessage(SEND_MESSAGE, jsonObject)
                viewModel.disconnectSocket()

                return@first true
            }
        }
    }

    private fun initRV() {
        binding.rvChat.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            adapter = chatAdapter
        }
    }

    private fun getChatMessages() {
        viewModel.getChatMessages(args.roomId)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getChatMessagesObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.chatResponse.collect {
                it.applyCommonSideEffects(this@ChatFragment) { response ->
                    response.data?.messages?.let { chats ->
                        messagesList.addAll(chats)
                        chatAdapter.submitList(messagesList)
                        chatAdapter.notifyDataSetChanged()
                        binding.rvChat.scrollToPosition(0)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        connectToSocket()
    }

    private fun connectToSocket() {
        viewModel.connectToSocket()
    }

    private fun connectToSocketListener() {
        lifecycleScope.launchWhenStarted {
            viewModel.connectToSocketResponse.onEach { isConnected ->
                if (isConnected) {
                    addUser()
                    openChannel()
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun addUser() {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.getUserData().first {
                val jsonObject = JSONObject()

                jsonObject.put(ROOM_ID, args.roomId)
                jsonObject.put(USER_ID, userId)

                viewModel.addUser(SUBSCRIBE_ROOM, jsonObject)

                return@first true
            }
        }
    }

    private fun openChannel() {
        viewModel.openChannel(RECEIVE_MESSAGE)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openChannelObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.newMessageFlow.collect {
                if (it.isNotEmpty()) {
                    val messages = it.map { message -> message.fromJson<MessagesItem>() }
                    messagesList.addAll(messages)
                    chatAdapter.submitList(messagesList)
                    chatAdapter.notifyDataSetChanged()
                    binding.rvChat.scrollToPosition(0)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        view?.hideKeyboard()
        exitChat()
    }

    private fun exitChat() {
        lifecycleScope.launchWhenCreated {
            viewModel.preferenceRepository.getUserData().first {
                val jsonObject = JSONObject()
                jsonObject.put(USER_ID, userId)
                jsonObject.put(ROOM_ID, args.roomId)
                viewModel.exitChat(UNSUBSCRIBE_ROOM, jsonObject)
                viewModel.disconnectSocket()

                return@first true
            }
        }
    }
}