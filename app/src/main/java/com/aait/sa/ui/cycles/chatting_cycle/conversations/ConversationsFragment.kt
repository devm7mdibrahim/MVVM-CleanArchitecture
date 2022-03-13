package com.aait.sa.ui.cycles.chatting_cycle.conversations

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aait.domain.entities.RoomsItem
import com.aait.sa.databinding.FragmentConversationsBinding
import com.aait.sa.ui.base.BaseFragment
import com.aait.sa.ui.utils.applyCommonSideEffects
import com.aait.utils.common.toGone
import com.aait.utils.common.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ConversationsFragment :
    BaseFragment<FragmentConversationsBinding>(FragmentConversationsBinding::inflate) {

    override val viewModel by viewModels<ConversationsViewModel>()
    private val conversationsAdapter by lazy { ConversationsAdapter { onConversationClicked(it) } }

    private fun onConversationClicked(roomsItem: RoomsItem) {
        findNavController().navigate(
            ConversationsFragmentDirections.actionGlobalChatFragment(
                roomId = 1,
                userName = "mohamed",
                uuid = "32131",
                otherId = 2
            )
        )
    }

    override fun startObserver() {
        super.startObserver()
        getRoomsObserver()
    }

    override fun afterCreateView() {
        super.afterCreateView()
        initRV()
        getRooms()
    }

    private fun initRV() {
        binding.rvConversations.apply {
            adapter = conversationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getRooms() {
        viewModel.getRooms()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getRoomsObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.roomsResponse.collect {
                it.applyCommonSideEffects(this@ConversationsFragment) { response ->
                    response.data?.rooms?.let { rooms ->
                        if (rooms.isNotEmpty()) {
                            binding.tvEmpty.toGone()
                            binding.rvConversations.toVisible()
                            conversationsAdapter.submitList(rooms)
                            conversationsAdapter.notifyDataSetChanged()
                        } else {
                            binding.tvEmpty.toVisible()
                            binding.rvConversations.toGone()
                        }
                    }
                }
            }
        }
    }
}