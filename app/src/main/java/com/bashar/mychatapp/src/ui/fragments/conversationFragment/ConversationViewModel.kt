package com.bashar.mychatapp.src.ui.fragments.conversationFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashar.mychatapp.src.data.Repository
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.data.models.Message
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.utils.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    var chat: Chat? = null

    var messageList: MutableListLiveData<Message?>? = MutableListLiveData(mutableListOf())

    var receiverUser: User? = null

    init {
        chat = savedStateHandle.get<Chat>("Chat")
        receiverUser = chat?.receiver
        println("CONVERSATION_FRAGMENT: ${chat?.receiver?.name}")
        getMessages(chat?.id ?: 1)
    }

    private fun getMessages(chatId: Int) = viewModelScope.launch {

        repository.getMessages(chatId).collect { messages ->
//            chatsList?.addAll(chats)
            messageList?.clear()
            messageList?.addAll(messages)
//            messages.forEach {
//                println(it.toMessage().toString())
//                messageList?.add(it.toMessage())
//            }
            println("MessagesSize: ${messageList?.size}")

        }
    }

    val newMessageText = MutableLiveData<String?>("")

    fun sendMessagePressed() = viewModelScope.launch{
        if (!newMessageText.value.isNullOrBlank()) {

            println("NEW_MESSAGE: ${newMessageText.value}")

            val newMsg = Message(
                message = newMessageText.value!!,
                senderId = chat?.sender?.id!!,
                receiverId = chat?.receiver?.id!!,
                timestamp = System.currentTimeMillis(),
                type = "msg",
                chatId = chat!!.id,
            )
            println("NEW_MESSAGE: ${newMsg}")

            repository.insertMessage(newMsg)
//           dbRepository.updateNewMessage(chatID, newMsg)
//           dbRepository.updateChatLastMessage(chatID, newMsg)
            newMessageText.value = ""
        }
    }

    val isRecording = MutableLiveData<Boolean>(false)

    fun recordVoice() = viewModelScope.launch {
        isRecording.value?.let {
            isRecording.value = !it
        }
    }

    fun stopRecordVoice() = viewModelScope.launch {
        isRecording.value?.let {
            val newVoiceMessage = Message(
                message = "/file_path.mp3",
                senderId = chat?.sender?.id!!,
                receiverId = chat?.receiver?.id!!,
                timestamp = System.currentTimeMillis(),
                type = "rec",
                chatId = chat!!.id,
            )
            println("NEW_VOICE_MESSAGE: $newVoiceMessage")
            repository.insertMessage(newVoiceMessage)

            isRecording.value = !it
        }
    }

}