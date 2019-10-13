package com.raza.albumviewer.datasource.network

import com.raza.albumviewer.BuildConfig
import com.raza.albumviewer.base.BaseDataSource
import com.raza.albumviewer.di.OpenForTesting
import com.raza.albumviewer.util.prefs.ALBUM_PARAMM
import com.raza.albumviewer.util.prefs.ARTIST_PARAMM
import com.raza.albumviewer.util.prefs.METHOD_PARAMM
import retrofit2.Response
import javax.inject.Inject

/**
 * Works with the LastAPI API to get data.
 */
@OpenForTesting
class RemoteDataSource @Inject constructor(private val service: LastFMAPIService) : BaseDataSource() {

    suspend fun searchArtist(page: Int, pageSize: Int? = null, params: HashMap<String, Any>? = null)
            = getResult { service.searchArtist(params?.get(METHOD_PARAMM) as String, params?.get(
        ARTIST_PARAMM) as String, BuildConfig.API_KEY, pageSize, page) }

    suspend fun getTopAlbums(page: Int, pageSize: Int? = null, params: HashMap<String, Any>? = null)
            = getResult { service.getTopAlbums(params?.get(METHOD_PARAMM) as String, params?.get(
        ARTIST_PARAMM) as String, BuildConfig.API_KEY, pageSize, page) }

    suspend fun getAlbumInfo(params: HashMap<String, Any>? = null)
            = getResult { service.getAlbumInfo(params?.get(METHOD_PARAMM) as String, params?.get(
        ALBUM_PARAMM) as String, params?.get(ARTIST_PARAMM) as String, BuildConfig.API_KEY) }


    suspend fun defaultFunction() = getResult { getDefaultResult() }


    private fun getDefaultResult(): Response<String> {
        return Response.success("")

    }
}