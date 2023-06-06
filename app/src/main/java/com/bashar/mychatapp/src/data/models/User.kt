package com.bashar.mychatapp.src.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val image: String,
) : Parcelable
