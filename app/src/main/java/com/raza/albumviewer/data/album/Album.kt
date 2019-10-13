package com.raza.albumviewer.data.album

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raza.albumviewer.data.Image
import com.raza.albumviewer.data.artist.Artist

data class Album(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("artist")
    var artistObject: Artist, // Cher
    @SerializedName("image")
    var image: List<Image>,
    @SerializedName("listeners")
    var listeners: String, // 394628
    @SerializedName("mbid")
    var mbid: String, // 63b3a8ca-26f2-4e2b-b867-647a6ec2bebd
    @SerializedName("name")
    var name: String, // Believe
    @SerializedName("playcount")
    var playcount: String, // 2541230
    @SerializedName("url")
    var url: String // https://www.last.fm/music/Cher/Believe
)