package com.example.appcraft.model.photos

data class PhotosListItem(
    val albumId: Long,
    val id: Long,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)