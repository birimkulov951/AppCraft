package com.example.appcraft.db.albums

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "albums_table")
data class AlbumEntity (

    @ColumnInfo(name = "albumId")
    var albumId: Long,

    @ColumnInfo(name = "albumTitle")
    var albumTitle: String,

    @ColumnInfo(name = "userId")
    var userId: Long

) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}