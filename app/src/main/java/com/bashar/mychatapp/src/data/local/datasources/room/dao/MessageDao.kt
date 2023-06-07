package com.bashar.mychatapp.src.data.local.datasources.room.dao

import androidx.room.*
import com.bashar.mychatapp.src.data.local.datasources.room.entity.MessageEntity
import kotlinx.coroutines.flow.Flow



@Dao
interface MessageDao{

    @Query("SELECT * FROM messages WHERE chat_id = :chatId ORDER BY timestamp ASC")
    fun getChatMessages(chatId: Int): Flow<List<MessageEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(messageEntity: MessageEntity)

    @Delete
    fun deleteMessage(messageEntity: MessageEntity?)

    @Query("DELETE FROM messages")
    fun deleteAllMessages()
}