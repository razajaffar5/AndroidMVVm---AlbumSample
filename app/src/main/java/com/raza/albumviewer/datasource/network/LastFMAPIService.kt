package com.raza.albumviewer.datasource.network

import com.raza.albumviewer.util.prefs.*
import com.raza.albumviewer.BuildConfig
import com.raza.albumviewer.data.network.AlbumResponse
import com.raza.albumviewer.data.network.ArtistResponse
import com.raza.albumviewer.data.network.AlbumDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Lego REST API access points
 */
interface LastFMAPIService {

    companion object {
        const val ENDPOINT = BuildConfig.BASE_URL
    }
    @GET("2.0/")
    suspend fun searchArtist(@Query(METHOD_PARAMM ) method: String? = null,
                             @Query(ARTIST_PARAMM) artist: String? = null,
                             @Query(API_KEY_PARAM) apiKey: String? = null,
                             @Query(LIMIT_PARAM) limit: Int? = null,
                             @Query(PAGE_NUMBER) page: Int? = null,
                             @Query(FORMAT_PARAM) format: String = FORMAT_VALUE): Response<ArtistResponse>

    @GET("2.0/")
    suspend fun getTopAlbums(@Query(METHOD_PARAMM ) method: String? = null,
                             @Query(ARTIST_PARAMM) artist: String? = null,
                             @Query(API_KEY_PARAM) apiKey: String? = null,
                             @Query(LIMIT_PARAM) limit: Int? = null,
                             @Query(PAGE_NUMBER) page: Int? = null,
                             @Query(FORMAT_PARAM) format: String = FORMAT_VALUE): Response<AlbumResponse>

    @GET("2.0/")
    suspend fun getAlbumInfo(@Query(METHOD_PARAMM ) method: String? = null,
                             @Query(ALBUM_PARAMM) album: String? = null,
                             @Query(ARTIST_PARAMM) artist: String? = null,
                             @Query(API_KEY_PARAM) apiKey: String? = null,
                             @Query(FORMAT_PARAM) format: String = FORMAT_VALUE): Response<AlbumDetailResponse>
}