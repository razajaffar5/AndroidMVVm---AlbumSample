package com.raza.albumviewer.data.network

import com.google.gson.annotations.SerializedName
import com.raza.albumviewer.data.album.Album

data class AlbumResponse(
    @SerializedName("topalbums")
    var topalbums: TopAlbums
) {
    data class TopAlbums(
        @SerializedName("album")
        var album: List<Album>,
        @SerializedName("@attr")
        var attr: Attr
    )
}