package com.bashar.mychatapp.src.data.models

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: Int? = 0,
    val senderId: Int,
    val receiverId: Int,
    val chatId: Int,
    val message: String,
    val type: String,
    val timestamp: Long,
) : Parcelable {

    @IgnoredOnParcel
    val isPlaying = MutableLiveData<Boolean>(false)

}
