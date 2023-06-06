package com.bashar.mychatapp.src.ui.fragments.chatsFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashar.mychatapp.src.data.Repository
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

    var usersList: MutableListLiveData<User?>? = MutableListLiveData(mutableListOf())

    init {
        user = savedStateHandle.get<User>("User")
        println("CHAT_FRAGMENT: ${user?.name}")
    }

    private fun showAllUsers() = viewModelScope.launch {
        repository.getAllUsers().collect{ users ->
            users.forEach {
                println(it.toUser().toString())
                usersList?.add(it.toUser())
            }
        }
    }

}