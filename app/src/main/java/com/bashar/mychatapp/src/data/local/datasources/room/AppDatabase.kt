package com.bashar.mychatapp.src.data.local.datasources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bashar.mychatapp.src.data.local.datasources.room.convertor.DateConverter
import com.bashar.mychatapp.src.data.local.datasources.room.dao.TableDao
import com.bashar.mychatapp.src.data.local.datasources.room.dao.UserDao
import com.bashar.mychatapp.src.data.local.datasources.room.entity.TableEntity
import com.bashar.mychatapp.src.data.local.datasources.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        val DATABASE_NAME = "myAppdb"
    }

//    abstract fun tableDao(): TableDao
    abstract fun userDao(): UserDao

}