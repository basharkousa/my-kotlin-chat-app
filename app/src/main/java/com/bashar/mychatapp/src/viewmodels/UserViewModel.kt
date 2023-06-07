package com.bashar.mychatapp.src.viewmodels

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
class UserViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    var list  = mutableListOf<String>("Bahsra","Kousa","mksjkao")
    var message = "Hello My Templateeeee"


    var usersList: MutableListLiveData<User?>? = MutableListLiveData(mutableListOf())

    init {
        showAllUsers()
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