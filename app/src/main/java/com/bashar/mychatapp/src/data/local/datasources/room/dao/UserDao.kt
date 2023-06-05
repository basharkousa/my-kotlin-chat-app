package com.bashar.mychatapp.src.data.local.datasources.room.dao

import androidx.room.*
import com.bashar.mychatapp.src.data.local.datasources.room.entity.UserEntity
import com.bashar.mychatapp.src.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @get:Query("SELECT * FROM Users")
    val allUsers: Flow<List<UserEntity>>

    @get:Query("SELECT * FROM Users ORDER BY id LIMIT 1")
    val user: Flow<UserEntity>

////    // Using Paging
////    @get:Query("SELECT * FROM albums")
////    val allAlbumsPerPage: DataSource.Factory<Int?, Album?>?

    @Query("SELECT * FROM Users WHERE email = :email")
    fun getUserByEmail(email: String?): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(users: List<User>) {
//        TODO("Not yet implemented")
//    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity?)

    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}

