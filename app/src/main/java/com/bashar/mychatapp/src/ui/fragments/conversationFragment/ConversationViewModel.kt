package com.bashar.mychatapp.src.ui.fragments.conversationFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashar.mychatapp.src.data.Repository
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.utils.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){


    var chat: Chat? = null

    var chatsList: MutableListLiveData<Chat?>? = MutableListLiveData(mutableListOf())

    init {
        chat = savedStateHandle.get<Chat>("Chat")
        println("CONVERSATION_FRAGMENT: ${chat?.receiver?.name}")
    }

    private fun getAllChats(userId: Int) = viewModelScope.launch {

        repository.getAllChats(userId).collect{ chats ->
//            chatsList?.addAll(chats)
            chats.forEach {
                println(it.toString())
                chatsList?.add(it)
            }
        }
    }

}