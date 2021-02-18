package com.example.appcraft.db.photos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "photos_table")
data class PhotoEntity (

    @ColumnInfo(name = "albumId")
    var albumId: Long,

    @ColumnInfo(name = "photoId")
    var photoId: Long,

    @ColumnInfo(name = "photoTitle")
    var photoTitle: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "thumbnailUrl")
    var thumbnailUrl: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}