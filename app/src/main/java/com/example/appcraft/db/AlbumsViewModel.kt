package com.example.appcraft.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appcraft.db.albums.AlbumEntity
import com.example.appcraft.db.albums.AlbumsRoomDatabase
import com.example.appcraft.db.photos.PhotoEntity
import com.example.appcraft.db.photos.PhotosRoomDatabase
import com.example.appcraft.utils.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    val allAlbums: LiveData<List<AlbumEntity>>
    val allPhotos: LiveData<List<PhotoEntity>>

    // SharedPref AlbumId

    init {

        val albumsDao = AlbumsRoomDatabase.getAlbumsDatabase(application).albumsDao()
        val photosDao = PhotosRoomDatabase.getPhotosDatabase(application).photosDao()

        repository = Repository(albumsDao,photosDao,SharedPreferenceUtil.getAlbumIdPref(getApplication()))

        allAlbums = repository.allAlbums
        allPhotos = repository.allPhotos

    }

    /**
     * Launching a new coroutine to insert/update/delete a in a non-blocking way
     */
    fun insertAlbum(album: AlbumEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAlbum(album)
    }

    fun updateAlbum(album: AlbumEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAlbum(album)
    }

    fun deleteAlbum(album: AlbumEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAlbum(album)
    }

    fun deleteAllAlbums() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllAlbums()
    }

    fun deleteAlbumById(albumId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAlbumById(albumId)
    }




    fun insertPhoto(photo: PhotoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPhoto(photo)
    }

    fun updatePhoto(photo: PhotoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePhoto(photo)
    }

    fun deletePhoto(photo: PhotoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePhoto(photo)
    }

    fun deleteAllPhotos() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllPhotos()
    }


}