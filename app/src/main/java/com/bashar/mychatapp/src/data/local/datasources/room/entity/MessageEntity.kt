package com.bashar.mychatapp.src.data.local.datasources.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.bashar.mychatapp.src.data.models.Message

@Entity(tableName = "messages",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["sender_id"]),
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["receiver_id"]),
        ForeignKey(entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chat_id"])
    ])
data class MessageEntity(
    @PrimaryKey val id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val chat_id: Int,
    val message: String,
    val type: String,
    val timestamp: Long
){
    companion object {
        fun from(message: Message): MessageEntity{
            return MessageEntity(
                id = message.id,
                type = message.type,
                chat_id = message.chatId,
                sender_id = message.senderId,
                receiver_id = message.receiverId,
                message = message.message,
                timestamp = message.timestamp
            )
        }
    }

    fun toMessage(): Message {
        return Message(
            id = id,
            type = type,
            chatId = chat_id,
            senderId = sender_id,
            receiverId = receiver_id,
            message = message,
            timestamp = timestamp
        )
    }
}


