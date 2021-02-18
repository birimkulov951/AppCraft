package com.example.appcraft.utils

import android.content.Context
import android.location.Location
import androidx.core.content.edit
import com.example.appcraft.R

/**
 * Provides access to constants.
 */
internal object Constants{

    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    const val ALBUM_ID_DATA = "ALBUM_ID_DATA"
    const val ALBUM_TITLE_DATA = "ALBUM_TITLE_DATA"
    const val ALBUM_USER_ID = "ALBUM_USER_ID"

    const val PHOTO_URL = "PHOTO_URL"
    const val PHOTO_TITLE = "PHOTO_TITLE"
    const val PHOTO_ID = "PHOTO_ID"

}
/**
 * Provides access to SharedPreferences.
 */
internal object SharedPreferenceUtil {

    const val KEY_FOREGROUND_ENABLED = "KEY_FOREGROUND_ENABLED"
    const val KEY_ALBUM_ID = "KEY_ALBUM_ID"

    /**
     * Returns true if requesting location updates, otherwise returns false.
     */
    fun getLocationTrackingPref(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            .getBoolean(KEY_FOREGROUND_ENABLED, false)

    /**
     * Stores the location updates state in SharedPreferences.
     */
    fun saveLocationTrackingPref(context: Context, requestingLocationUpdates: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE).edit {
                putBoolean(KEY_FOREGROUND_ENABLED, requestingLocationUpdates)
            }

    /**
     * Returns albumId if requesting location updates, otherwise returns -1L.
     */
    fun getAlbumIdPref(context: Context): Long =
            context.getSharedPreferences(
                    context.getString(R.string.preference_album_id_key), Context.MODE_PRIVATE)
                    .getLong(KEY_ALBUM_ID, -1L)

    /**
     * Stores the AlbumId state in SharedPreferences.
     */
    fun saveAlbumIdPref(context: Context, requestingLocationUpdates: Long) =
            context.getSharedPreferences(
                    context.getString(R.string.preference_album_id_key),
                    Context.MODE_PRIVATE).edit {
                putLong(KEY_ALBUM_ID, requestingLocationUpdates)
            }

}

fun Location?.toText(): String {
    return if (this != null) {
        "$latitude - $longitude"
    } else {
        "Unknown location"
    }
}
