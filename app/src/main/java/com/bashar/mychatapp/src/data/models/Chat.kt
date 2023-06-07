package com.bashar.mychatapp.src.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val id: Int,
    val user1_id: Int,
    val user2_id: Int,
    var receiver: User = User(0,"User","","", image = "")
) : Parcelable
