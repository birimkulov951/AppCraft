package com.example.appcraft.retrofit

import com.example.appcraft.model.albums.AlbumsList
import com.example.appcraft.model.albums.AlbumsListItem
import com.example.appcraft.model.photos.PhotosList
import com.example.appcraft.model.photos.PhotosListItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/albums")
    fun getAlbumsList(): Call<MutableList<AlbumsListItem>>

    @GET("/photos")
    fun getPhotosListByAlbumId(@Query("albumId") albumId: Long): Call<MutableList<PhotosListItem>>

}