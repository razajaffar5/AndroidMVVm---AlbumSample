package com.raza.albumviewer.data.network

import com.google.gson.annotations.SerializedName
import com.raza.albumviewer.data.artist.Artist

data class ArtistResponse(
    @SerializedName("results")
    val results: Results
    ) {
    data class Results(
        @SerializedName("artistmatches")
        var artistmatches: Artistmatches,
        @SerializedName("@attr")
        var attr: Attr,
        @SerializedName("opensearch:itemsPerPage")
        var opensearchItemsPerPage: String, // 1
        @SerializedName("opensearch:Query")
        var opensearchQuery: OpensearchQuery,
        @SerializedName("opensearch:startIndex")
        var opensearchStartIndex: String, // 0
        @SerializedName("opensearch:totalResults")
        var opensearchTotalResults: String // 17533273
    ) {
        data class Artistmatches(
            @SerializedName("artist")
            var artist: List<Artist>
        )

        data class OpensearchQuery(
            @SerializedName("role")
            var role: String, // request
            @SerializedName("searchTerms")
            var searchTerms: String, // a
            @SerializedName("startPage")
            var startPage: String, // 1
            @SerializedName("#text")
            var text: String
        )
    }
}

