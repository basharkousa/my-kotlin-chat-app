package com.bashar.mychatapp.src.data.local.datasources.room.dao

import androidx.room.*
import com.bashar.mychatapp.src.data.local.datasources.room.entity.ChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao{

    @Query("SELECT * FROM chats WHERE user1_id = :userId OR user2_id = :userId")
    fun getUserChats(userId: Int): Flow<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chatEntity: ChatEntity)

    @Delete
    fun deleteChat(chatEntity: ChatEntity?)

    @Query("DELETE FROM chats")
    fun deleteAllChats()
}

