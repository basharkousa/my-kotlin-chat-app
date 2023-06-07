package com.bashar.mychatapp.src.data.local.datasources.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashar.mychatapp.src.data.models.Chat


@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: Int,
    val user1_id: Int,
    val user2_id: Int
){

    companion object {
        fun from(chat: Chat): ChatEntity{
            return ChatEntity(
                id = chat.id,
                user1_id = chat.user1_id,
                user2_id = chat.user2_id
            )
        }
    }

    fun toChat(): Chat{
        return Chat(
            id = id,
            user1_id = user1_id,
            user2_id = user2_id,
        )
    }

}
