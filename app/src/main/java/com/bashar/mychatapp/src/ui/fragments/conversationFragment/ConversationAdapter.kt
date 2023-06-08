package com.bashar.mychatapp.src.ui.fragments.conversationFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.databinding.CardMessageMeBinding
import com.bashar.mychatapp.databinding.CardMessageOtherBinding
import com.bashar.mychatapp.src.data.models.Message
import androidx.recyclerview.widget.DiffUtil
import com.bashar.mychatapp.databinding.CardVoiceMessageMeBinding
import com.bashar.mychatapp.databinding.CardVoiceMessageOtherBinding


class ConversationAdapter internal constructor(private val viewModel: ConversationViewModel, private val userId: Int)
    : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    private val holderTypeMessageReceived = 1
    private val holderTypeMessageSent = 2
    private val holderTypeVoiceMessageSent = 3
    private val holderTypeVoiceMessageReceived = 4

    class SentViewHolder(private val binding: CardMessageMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ConversationViewModel, item: Message) {
            binding.viewModel = viewModel
            binding.model = item
            binding.executePendingBindings()
        }
    }

    class ReceivedViewHolder(private val binding:CardMessageOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ConversationViewModel, item: Message) {
            binding.viewModel = viewModel
            binding.model = item
            binding.executePendingBindings()
        }
    }

    class SentVoiceViewHolder(private val binding: CardVoiceMessageMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ConversationViewModel, item: Message) {
            binding.viewModel = viewModel
            binding.model = item
            binding.executePendingBindings()
        }
    }

    class ReceivedVoiceViewHolder(private val binding: CardVoiceMessageOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ConversationViewModel, item: Message) {
            binding.viewModel = viewModel
            binding.model = item
            binding.executePendingBindings()
        }
    }



    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderId != userId) {
            when(getItem(position).type){
                "rec"-> holderTypeVoiceMessageReceived
                else -> holderTypeMessageReceived
            }
        } else {
            when(getItem(position).type){
                "rec"-> holderTypeVoiceMessageSent
                else -> holderTypeMessageSent

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            holderTypeMessageSent -> (holder as SentViewHolder).bind(
                viewModel,
                getItem(position)
            )
            holderTypeMessageReceived -> (holder as ReceivedViewHolder).bind(
                viewModel,
                getItem(position)
            )
            holderTypeVoiceMessageReceived -> (holder as ReceivedVoiceViewHolder).bind(
                viewModel,
                getItem(position)
            )
            holderTypeVoiceMessageSent -> (holder as SentVoiceViewHolder).bind(
                viewModel,
                getItem(position)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            holderTypeMessageSent -> {
                val binding = CardMessageMeBinding.inflate(layoutInflater, parent, false)
                SentViewHolder(binding)
            }
            holderTypeMessageReceived -> {
                val binding = CardMessageOtherBinding.inflate(layoutInflater, parent, false)
                ReceivedViewHolder(binding)
            }

            holderTypeVoiceMessageReceived -> {
                val binding = CardVoiceMessageOtherBinding.inflate(layoutInflater, parent, false)
                ReceivedVoiceViewHolder(binding)
            }

            holderTypeVoiceMessageSent -> {
                val binding = CardVoiceMessageMeBinding.inflate(layoutInflater, parent, false)
                SentVoiceViewHolder(binding)
            }

            else -> {
                throw Exception("Error reading holder type")
            }
        }
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.timestamp == newItem.timestamp
    }
}