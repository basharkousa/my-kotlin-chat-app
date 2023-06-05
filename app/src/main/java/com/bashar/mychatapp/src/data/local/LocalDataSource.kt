package com.bashar.mychatapp.src.data.local


import androidx.room.Transaction
import com.bashar.mychatapp.src.data.local.datasources.room.dao.TableDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.UserDao
import com.bashar.mychatapp.src.data.local.datasources.room.entity.UserEntity
import com.bashar.mychatapp.src.data.models.User
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSource @Inject constructor(private val userDao: UserDao) {



    @Transaction
    fun doAllTransActions() {
        val users = listOf(
            User(1, "Bashar", "basharkousax@gmail.com", password = "123456789"),
            User(2, "Osama", "osamaspero@gmail.com", password = "123456789"),
        )
        users.forEach { user ->
            userDao.insertUser(UserEntity.from(user))
        }

//        val chats = listOf(
//            Chat(1, 1, 2),
//            Chat(2, 1, 3),
//            Chat(3, 2, 3)
//        )
//        chatDao.insertAll(chats)
//
//        val messages = listOf(
//            Message(1, 1, "Hello", System.currentTimeMillis()),
//            Message(2, 2, "Hi there", System.currentTimeMillis() + 1000),
//            Message(3, 1, "How are you?", System.currentTimeMillis() + 2000),
//            Message(4, 3, "Hey guys", System.currentTimeMillis() + 3000),
//            Message(5, 2, "Doing well, thanks", System.currentTimeMillis() + 4000),
//            Message(6, 3, "Same here", System.currentTimeMillis() + 5000),
//            Message(7, 1, "Great!", System.currentTimeMillis() + 6000)
//        )
//        messageDao.insertAll(messages)
    }


    fun getUser(): Flow<UserEntity?> = userDao.user
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.allUsers
    fun getUserByEmail(email: String): Flow<UserEntity?> = userDao.getUserByEmail(email)
    fun saveUser(user: User) = userDao.insertUser(UserEntity.from(user))
    fun updateUser(user: User) = userDao.updateUser(UserEntity.from(user))
    fun deleteUser(user: User) = userDao.deleteUser(UserEntity.from(user))
    fun deleteAllUser() = userDao.deleteAllUsers()

//    fun getLocalAlbums(): Flow<List<AlbumEntity>> = albumDao.allAlbums
//
//    fun getAlbumById(album: Album): Flow<AlbumEntity> = albumDao.getAlbumById(album.mbid)
//
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