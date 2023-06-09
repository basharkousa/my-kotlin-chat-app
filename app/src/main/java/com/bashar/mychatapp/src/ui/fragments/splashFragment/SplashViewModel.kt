package com.bashar.mychatapp.src.ui.fragments.splashFragment

import android.util.Log
import androidx.lifecycle.*
import com.bashar.mychatapp.src.data.Repository
import com.bashar.mychatapp.src.data.local.datasources.room.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val isFirstLaunchLiveData = repository.isFirstLaunch().asLiveData()
    var isFirstLaunch = false

    init {
        println("TRANSACTIONS STARTS HERE:")
//        doTransactions()
        showAllUsers()
        getUserByEmail()
        goToUsersPage()
    }

    fun setFirstLaunch() = viewModelScope.launch {
        repository.toggleIsFirstLaunch()
    }

    var navigateLivedata: MutableLiveData<Boolean> = MutableLiveData(false)
    fun goToUsersPage() = viewModelScope.launch {
        delay(1300)
        navigateLivedata.value = true
    }

    fun doTransactions() = viewModelScope.launch {
        repository.doAllTransActions()
    }

    private fun showAllUsers() = viewModelScope.launch {
        repository.getAllUsers().collect { users ->
            users.forEach {
                println(it.toUser().toString())
            }
        }
    }

    private fun getUserByEmail(email: String = "basharkousax@gmail.com") = viewModelScope.launch {
        repository.getUserUserByEmail(email).collect { user ->
            if (user != null) {
                println("LOGGED_USER: $user")
            } else {
                println("LOGGED_USER: User Not Found")
            }
        }
    }
}

