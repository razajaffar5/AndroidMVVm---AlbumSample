package com.raza.albumviewer.datasource.localdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raza.albumviewer.data.Image
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.artist.Artist
import com.raza.albumviewer.data.network.AlbumDetailResponse
import java.util.Collections.emptyList


class AppTypeConverters {

    @TypeConverter
    fun storedStringToImages(data: String?): List<Image> {
        val gson = Gson()
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Image>>() {

        }.type
        return gson.fromJson<List<Image>>(data, listType)
    }

    @TypeConverter
    fun imagesToStoredString(myObjects: List<Image>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun storedStringToTracks(data: String?): List<AlbumDetailResponse.AlbumItem.Tracks.Track> {
        val gson = Gson()
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<AlbumDetailResponse.AlbumItem.Tracks.Track>>() {

        }.type
        return gson.fromJson<List<AlbumDetailResponse.AlbumItem.Tracks.Track>>(data, listType)
    }

    @TypeConverter
    fun tracksObjectToString(app: AlbumDetailResponse.AlbumItem.Tracks?): String = app?.let {
        Gson().toJson(app)
    }?:kotlin.run {
        ""
    }

    @TypeConverter
    fun stringToTracksObject(string: String): AlbumDetailResponse.AlbumItem.Tracks? = string?.let {
        if (!string.isEmpty()) {
            Gson().fromJson(string, AlbumDetailResponse.AlbumItem.Tracks::class.java)
        } else {
            return null
        }
    }

    @TypeConverter
    fun wikiToString(app: AlbumDetailResponse.AlbumItem.Wiki?): String = app?.let {
        Gson().toJson(app)
    }?:kotlin.run {
        ""
    }

    @TypeConverter
    fun stringToWiki(string: String): AlbumDetailResponse.AlbumItem.Wiki? = string?.let {
        if (string.isNotEmpty()) {
            Gson().fromJson(string, AlbumDetailResponse.AlbumItem.Wiki::class.java)
        } else {
            return null
        }
    }
    @TypeConverter
    fun artistToString(app: Artist?): String = app?.let {
        Gson().toJson(app)
    }?:kotlin.run {
        ""
    }

    @TypeConverter
    fun stringToArtist(string: String): Artist? = string?.let {
        if (string.isNotEmpty()) {
            Gson().fromJson(string,Artist::class.java)
        } else {
            return null
        }
    }

    @TypeConverter
    fun imageToString(app: Image?): String = app?.let {
        Gson().toJson(app)
    }?:kotlin.run {
        ""
    }

    @TypeConverter
    fun stringToImage(string: String): Image? = string?.let {
        if (string.isNotEmpty()) {
            Gson().fromJson(string, Image::class.java)
        } else {
            return null
        }
    }

}