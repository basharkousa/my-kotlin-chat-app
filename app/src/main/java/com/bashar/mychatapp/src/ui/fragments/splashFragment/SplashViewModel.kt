package com.bashar.mychatapp.src.ui.fragments.splashFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bashar.mychatapp.src.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
}