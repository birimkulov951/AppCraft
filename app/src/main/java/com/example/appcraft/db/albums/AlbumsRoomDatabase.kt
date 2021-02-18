package com.example.appcraft.db.albums

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlbumEntity::class], version = 1/*, exportSchema = false*/)
abstract class AlbumsRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AlbumsRoomDatabase? = null

        fun getAlbumsDatabase(context: Context): AlbumsRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AlbumsRoomDatabase::class.java, "albums_table").build()
                INSTANCE = instance
                return instance
            }
        }
    }

}