package com.raza.albumviewer.data.artist

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raza.albumviewer.data.Image

data class Artist(
    @PrimaryKey
    @SerializedName("mbid")
    var mbid: String, // ada7a83c-e3e1-40f1-93f9-3e73dbc9298a
    @SerializedName("listeners")
    var listeners: String, // 3556732
    @SerializedName("name")
    var name: String, // Arctic Monkeys
    @SerializedName("streamable")
    var streamable: String, // 0
    @SerializedName("url")
    var url: String, // https://www.last.fm/music/Arctic+Monkeys
    @SerializedName("image")
    var image: List<Image>
)