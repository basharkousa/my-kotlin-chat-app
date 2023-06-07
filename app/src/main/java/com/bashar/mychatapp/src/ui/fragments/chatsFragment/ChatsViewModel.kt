package com.bashar.mychatapp.src.ui.fragments.chatsFragment

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
class ChatsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){


    var user: User? = null

    var chatsList: MutableListLiveData<Chat?>? = MutableListLiveData(mutableListOf())

    init {
        user = savedStateHandle.get<User>("User")
        println("CHAT_FRAGMENT: ${user?.name}")
        getAllChats(user?.id?:1)
    }

    private fun getAllChats(userId: Int) = viewModelScope.launch {

        repository.getAllChats(userId).collect{ chats ->
            chats.forEach {
                println(it.toChat().toString())
                chatsList?.add(it.toChat())
            }
        }
    }

}