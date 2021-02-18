package com.example.appcraft.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.appcraft.db.albums.AlbumEntity
import com.example.appcraft.db.albums.AlbumsDAO
import com.example.appcraft.db.photos.PhotosDAO
import com.example.appcraft.db.photos.PhotoEntity
import com.example.appcraft.utils.SharedPreferenceUtil

class Repository(private val albumsDao: AlbumsDAO, private val photosDao: PhotosDAO, private val albumId: Long) {

    val allAlbums: LiveData<List<AlbumEntity>> = albumsDao.getAllAlbums()
    val allPhotos: LiveData<List<PhotoEntity>> = photosDao.getAllPhotos(albumId)


    /**
     *  CRUD methods of AlbumsDatabase
     */
    @WorkerThread
    suspend fun insertAlbum(album: AlbumEntity) {
        albumsDao.insertAlbum(album)
    }
    @WorkerThread
    suspend fun updateAlbum(album: AlbumEntity) {
        albumsDao.updateAlbum(album)
    }
    @WorkerThread
    suspend fun deleteAlbum(album: AlbumEntity) {
        albumsDao.deleteAlbum(album)
    }
    @WorkerThread
    suspend fun deleteAllAlbums() {
        albumsDao.deleteAllAlbums()
    }
    @WorkerThread
    suspend fun deleteAlbumById(albumId: Long) {
        albumsDao.deleteAlbumById(albumId)
    }


    /**
     *  CRUD methods of PhotosDatabase
     */
    @WorkerThread
    suspend fun insertPhoto(album: PhotoEntity) {
        photosDao.insertPhoto(album)
    }
    @WorkerThread
    suspend fun updatePhoto(album: PhotoEntity) {
        photosDao.updatePhoto(album)
    }
    @WorkerThread
    suspend fun deletePhoto(album: PhotoEntity) {
        photosDao.deletePhoto(album)
    }
    @WorkerThread
    suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }

}