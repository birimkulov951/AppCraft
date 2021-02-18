package com.example.appcraft.db.albums

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlbumsDAO {

    @Query("SELECT * FROM albums_table ORDER BY albumId ASC")
    fun getAllAlbums() : LiveData<List<AlbumEntity>>

    @Insert
    fun insertAlbum(album: AlbumEntity)

    @Update
    fun updateAlbum(album: AlbumEntity)

    @Delete
    fun deleteAlbum(album: AlbumEntity)

    @Query("DELETE FROM albums_table")
    fun deleteAllAlbums()

    @Query("DELETE FROM albums_table WHERE albumId = :desiredAlbumId")
    fun deleteAlbumById(desiredAlbumId: Long)

}