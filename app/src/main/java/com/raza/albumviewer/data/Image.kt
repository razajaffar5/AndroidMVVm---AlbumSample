package com.raza.albumviewer.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "image")
data class Image(
    @SerializedName("size")
    var size: String, // mega
    @SerializedName("#text")
    var text: String // https://lastfm-img2.akamaized.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png
)