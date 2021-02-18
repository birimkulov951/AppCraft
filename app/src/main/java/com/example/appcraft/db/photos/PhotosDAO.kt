package com.example.appcraft.db.photos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhotosDAO {

    @Query("SELECT * FROM photos_table WHERE albumId = :desiredAlbumId ORDER BY photoId ASC")
    fun getAllPhotos(desiredAlbumId: Long) : LiveData<List<PhotoEntity>>

    @Insert
    fun insertPhoto(photo: PhotoEntity)

    @Update
    fun updatePhoto(photo: PhotoEntity)

    @Delete
    fun deletePhoto(photo: PhotoEntity)

    @Query("DELETE FROM photos_table")
    fun deleteAllPhotos()

    @Query("DELETE FROM photos_table WHERE albumId = :desiredAlbumId")
    fun deletePhotoByAlbumId(desiredAlbumId: Long)

}