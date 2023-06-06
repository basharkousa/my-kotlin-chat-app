package com.bashar.mychatapp.src.data.local.datasources.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashar.mychatapp.src.data.models.User


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val password: String,
    var image:String
){

    companion object {
        fun from(user: User): UserEntity{
            return UserEntity(
                email = user.email ?: "invalidEmail",
                id = user.id,
                password = user.password,
                name = user.name,
                image = user.image
                )
        }
    }

    fun toUser(): User{
        return User(
            id = id,
            email = email,
            password = password,
            name = name,
            image = image
        )
    }

}
