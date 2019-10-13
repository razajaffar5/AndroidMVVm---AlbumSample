package com.raza.albumviewer.data.network

import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("artist")
    var artist: String, // A
    @SerializedName("page")
    var page: String, // 1
    @SerializedName("perPage")
    var perPage: String, // 1
    @SerializedName("total")
    var total: String, // 10988
    @SerializedName("totalPages")
    var totalPages: String // 10988
)
