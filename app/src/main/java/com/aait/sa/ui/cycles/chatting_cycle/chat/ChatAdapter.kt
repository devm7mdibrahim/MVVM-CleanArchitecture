package com.aait.sa.ui.cycles.chatting_cycle.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aait.domain.entities.MessagesItem
import com.aait.sa.R
import com.aait.sa.databinding.ItemReceiverMessageBinding
import com.aait.sa.databinding.ItemSenderMessageBinding

class ChatAdapter : ListAdapter<MessagesItem, RecyclerView.ViewHolder>(MessagesCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.item_sender_message -> {
                MessageSenderViewHolder(
                    ItemSenderMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.layout.item_receiver_message -> {
                MessageReceiverViewHolder(
                    ItemReceiverMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MessageSenderViewHolder -> {
                holder.bind(getItem(position))
            }

            is MessageReceiverViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderType == "provider") {
            R.layout.item_sender_message
        } else {
            R.layout.item_receiver_message
        }
    }

    inner class MessageSenderViewHolder(private val binding: ItemSenderMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messagesItem: MessagesItem?) {
            messagesItem?.let {
//                binding.ivImage.loadImageFromUrl(it.provider.image)
//                binding.tvMessageText.text = it.message
//                binding.tvMessageTime.text = it.time
            }
        }
    }

    inner class MessageReceiverViewHolder(private val binding: ItemReceiverMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messagesItem: MessagesItem?) {
            messagesItem?.let {
//                binding.ivImage.loadImageFromUrl(it.user.image)
//                binding.tvMessageText.text = it.message
//                binding.tvMessageTime.text = it.time
            }
        }
    }

    object MessagesCallback : DiffUtil.ItemCallback<MessagesItem>() {
        override fun areItemsTheSame(
            oldItem: MessagesItem,
            newItem: MessagesItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MessagesItem,
            newItem: MessagesItem
        ): Boolean {
            return false
        }
    }
}