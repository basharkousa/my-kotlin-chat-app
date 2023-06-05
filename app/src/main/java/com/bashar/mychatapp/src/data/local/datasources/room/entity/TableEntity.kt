package com.bashar.mychatapp.src.data.local.datasources.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table")
data class TableEntity(

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

) {
    companion object {
        fun from(table: Any): TableEntity {
            return TableEntity(
//                id = table.id?:"id",
//                name = table.name?:"name",
                id = "id",
                name = "name",
            )
        }
    }

//    fun toTable(): TableModel {
//        return TableModel(
//            mbid = mbid,
//            _name = name,
//        )
//    }
}



