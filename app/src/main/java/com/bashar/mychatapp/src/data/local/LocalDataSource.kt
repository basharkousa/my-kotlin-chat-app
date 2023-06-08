package com.bashar.mychatapp.src.data.local


import androidx.room.Transaction
import com.bashar.mychatapp.R
import com.bashar.mychatapp.src.data.local.datasources.room.dao.ChatDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.MessageDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.UserDao
import com.bashar.mychatapp.src.data.local.datasources.room.entity.ChatEntity
import com.bashar.mychatapp.src.data.local.datasources.room.entity.MessageEntity
import com.bashar.mychatapp.src.data.local.datasources.room.entity.UserEntity
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.data.models.Message
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.utils.BasicTools
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
) {

    @Transaction
    fun doAllTransActions() {

        messageDao.deleteAllMessages()
        chatDao.deleteAllChats()
        userDao.deleteAllUsers()

        val users = listOf(
            User(
                1,
                "Bashar",
                "basharkousax@gmail.com",
                password = "123456789",
                BasicTools.getURLForResource(R.drawable.basharkousa00945574056)
            ),
            User(
                2,
                "Osama",
                "osamaspero@gmail.com",
                password = "123456789",
                BasicTools.getURLForResource(R.drawable.iv_user_osama)
            ),
            User(
                3,
                "Ali",
                "alikousous@gmail.com",
                password = "123456789",
                BasicTools.getURLForResource(R.drawable.iv_user_ali)
            ),
        )
        users.forEach { user ->
            userDao.insertUser(UserEntity.from(user))
        }

        val chats = listOf(
            Chat(1, 1, 2),
            Chat(2, 1, 3),
            Chat(3, 2, 3),
        )
        chats.forEach { chat ->
            chatDao.insertChat(ChatEntity.from(chat))
        }


        val messages = listOf(
            Message(1, 1, 2, 1, "Hello Osama", "msg", System.currentTimeMillis()),
            Message(2, 2, 1, 1, "Hello Bashar", "msg", System.currentTimeMillis() + 10000),
            Message(3, 1, 2, 1, "How are you?", "msg", System.currentTimeMillis() + 20000),
            Message(4, 2, 1, 1, "Doing well, thanks", "msg", System.currentTimeMillis() + 30000),
            Message(5, 1, 2, 1, "Same here", "msg", System.currentTimeMillis() + 40000),
            Message(6, 2, 1, 1, "Great!", "msg", System.currentTimeMillis() + 50000),
            Message(7, 1, 2, 1, "What r u doing today?", "msg", System.currentTimeMillis() + 6000),

            Message(8, 1, 2, 1, "Hello Osama dn sdnsj ndaksjn dkjasndkjasndkjasndkjansdkjasndjknaskjdn jksadn dad nkasjdnkajsndjkasnd", "msg", System.currentTimeMillis() + 6500),
            Message(9, 2, 1, 1, "Hello Bashar", "msg", System.currentTimeMillis() + 7000),
            Message(10, 1, 2, 1, "H", "msg", System.currentTimeMillis() + 8000),
            Message(11, 2, 1, 1, "Doing well, thanks", "msg", System.currentTimeMillis() + 9000),
            Message(12, 1, 2, 1, "Same here", "msg", System.currentTimeMillis() + 10000),
            Message(13, 2, 1, 1, "Great!", "msg", System.currentTimeMillis() + 11000),
            Message(14, 1, 2, 1, "What r u doing today?", "msg", System.currentTimeMillis() + 12000),

            )
        messages.forEach { message ->
            messageDao.insertMessage(MessageEntity.from(message))
        }

    }


    //User
    fun getUser(): Flow<UserEntity?> = userDao.user
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.allUsers
    fun getUserByEmail(email: String): Flow<UserEntity?> = userDao.getUserByEmail(email)
    fun saveUser(user: User) = userDao.insertUser(UserEntity.from(user))
    fun updateUser(user: User) = userDao.updateUser(UserEntity.from(user))
    fun deleteUser(user: User) = userDao.deleteUser(UserEntity.from(user))
    fun deleteAllUser() = userDao.deleteAllUsers()

    //Chats
//    fun getAllChats(userId: Int): Flow<List<ChatEntity>> = chatDao.getUserChats(userId)

    fun getAllChats(userId: Int): Flow<List<Chat>> = flow {

        val chatList = mutableListOf<Chat>()

        chatDao.getUserChats(userId).collect { chatEntities ->
            chatEntities.forEach { chatEntity ->
                val chat = chatEntity.toChat()

                if (userId == chat.user2_id)
                    chat.receiver = userDao.getUserById(chat.user1_id).toUser()
                else
                    chat.receiver = userDao.getUserById(chat.user2_id).toUser()

                chatList.add(chat)
            }
            emit(chatList)
        }

//        localDataSource.getUserByEmail(email).collect{
//            if(it != null){
//                emit(it.toUser())
//            }else{
//                emit(null)
//            }
//        }
    }.flowOn(Dispatchers.IO)


//    fun getMessages(chatId: Int): Flow<List<MessageEntity>> = messageDao.getChatMessages(chatId)
    fun getMessages(chatId: Int): Flow<List<Message>> = flow {

        val messageList = mutableListOf<Message>()

        messageDao.getChatMessages(chatId).collect { messageEntities ->
            messageList.clear()
            messageEntities.forEach { messageEntity ->
                val message = messageEntity.toMessage()
                messageList.add(message)
            }
            emit(messageList)
        }

//        localDataSource.getUserByEmail(email).collect{
//            if(it != null){
//                emit(it.toUser())
//            }else{
//                emit(null)
//            }
//        }
    }.flowOn(Dispatchers.IO)



    fun insertMessage(message: Message) = messageDao.insertMessage(MessageEntity.from(message))


//    fun insertAlbum(album: Album) {
//        println("LocalDataSource ${album.name}")
////        albumDao.insertAlbum(
////            AlbumEntity(
////                mbid = "1",
////                name = "Bashar",
////                playcount = 1,
////                url = "oma",
////                image = "https:\\/\\/lastfm.freetls.fastly.net\\/i\\/u\\/34s\\/0db2be6f7dca7f6b97b2e96fa6ac2d54.jpg",
////                listeners = "100"
////            )
////        )
//
//        albumDao.insertAlbum(AlbumEntity.from(album))
//    }
//
//    fun updateAlbum(album: Album) = albumDao.updateAlbum(AlbumEntity.from(album))
//
//    fun deleteAlbum(album: Album) = albumDao.deleteAlbum(AlbumEntity.from(album))
//
//    fun deleteAll() = albumDao.deleteAll()
}