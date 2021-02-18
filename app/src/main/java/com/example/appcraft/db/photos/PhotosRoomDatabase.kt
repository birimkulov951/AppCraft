package com.example.appcraft.db.photos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class], version = 1)
abstract class PhotosRoomDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: PhotosRoomDatabase? = null

        fun getPhotosDatabase(context: Context): PhotosRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, PhotosRoomDatabase::class.java, "photos_table").build()
                INSTANCE = instance
                return instance
            }
        }
    }

}