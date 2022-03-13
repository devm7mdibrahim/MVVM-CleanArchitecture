package com.aait.sa.ui.cycles.chatting_cycle.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.aait.domain.entities.RoomsItem
import com.aait.sa.databinding.ItemConversationBinding
import com.aait.sa.ui.base.BaseRecyclerAdapter
import com.aait.sa.ui.base.BaseViewHolder

class ConversationsAdapter constructor(private val onItemClick: (room: RoomsItem) -> Unit) :
    BaseRecyclerAdapter<RoomsItem, ItemConversationBinding, ConversationsAdapter.ConversationsViewHolder>(
        ConversationsCallBack
    ) {

    inner class ConversationsViewHolder(private val viewBinding: ItemConversationBinding) :
        BaseViewHolder<RoomsItem, ItemConversationBinding>(viewBinding) {
        override fun bind() {
            getRowItem()?.let { room ->
                with(viewBinding) {
//                    ivImage.loadImageFromUrl(room.user.image)
//                    tvName.text = room.user.name
//                    tvLastMsg.text = room.message
//                    tvDate.text = room.time
                }

                itemView.setOnClickListener { onItemClick.invoke(room) }
            }
        }
    }

    object ConversationsCallBack : DiffUtil.ItemCallback<RoomsItem>() {
        override fun areItemsTheSame(
            oldItem: RoomsItem,
            newItem: RoomsItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RoomsItem,
            newItem: RoomsItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsViewHolder {
        return ConversationsViewHolder(
            ItemConversationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}