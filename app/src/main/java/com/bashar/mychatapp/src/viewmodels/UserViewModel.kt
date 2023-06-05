package com.bashar.mychatapp.src.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bashar.mychatapp.src.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    var list  = mutableListOf<String>("Bahsra","Kousa","mksjkao")
    var message = "Hello My Templateeeee"


}