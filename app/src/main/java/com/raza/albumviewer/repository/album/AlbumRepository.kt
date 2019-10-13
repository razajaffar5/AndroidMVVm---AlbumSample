package com.raza.albumviewer.repository.album

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.album.AlbumDao
import com.raza.albumviewer.data.network.AlbumDetailResponse
import com.raza.albumviewer.datasource.network.RemoteDataSource
import com.raza.albumviewer.di.OpenForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class AlbumRepository @Inject constructor(
    private val dao: AlbumDao, private val remoteDataSource: RemoteDataSource
) {

    fun observePagedSets(
        connectivityAvailable: Boolean, params: HashMap<String, Any>,
        coroutineScope: CoroutineScope
    ) = observeRemotePagedSets(params, coroutineScope)

/*    private fun observeLocalPagedSets(keyword: String): LiveData<PagedList<Album>> {
        val dataSourceFactory = dao.searchAlbumPaged(keyword)

        return LivePagedListBuilder(
            dataSourceFactory, AlbumPageDataSourceFactory.pagedListConfig()
        ).build()
    }*/

    private fun observeRemotePagedSets(
        params: HashMap<String, Any>?,
        ioCoroutineScope: CoroutineScope
    )
            : LiveData<PagedList<Album>> {
        val dataSourceFactory = AlbumPageDataSourceFactory(
            params, remoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            AlbumPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    suspend fun observeAlbumDetail(params: HashMap<String, Any>) =
        remoteDataSource.getAlbumInfo(params)


    fun saveAlbum(savedAlbumItem: AlbumDetailResponse.AlbumItem, ioCoroutineScope: CoroutineScope) {
        ioCoroutineScope.launch {
            dao.insertItem(savedAlbumItem)
        }
    }

    fun deleteAlbum(savedAlbumItem: AlbumDetailResponse.AlbumItem, ioCoroutineScope: CoroutineScope) {
        ioCoroutineScope.launch {
            dao.deleteItem(savedAlbumItem)
        }
    }

    fun loadSavedAlbums(): LiveData<List<AlbumDetailResponse.AlbumItem>> {
        return dao.getSavedAlbums()
    }

    fun loadAlbumDetails(albumId: Int): LiveData<AlbumDetailResponse.AlbumItem> {
        return dao.loadAlbumDetailsById(albumId)
    }

    fun loadAlbumDetailsByName(album: String, artist: String): LiveData<AlbumDetailResponse.AlbumItem> {
        return dao.loadAlbumDetailsByName(album, artist)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AlbumRepository? = null

        fun getInstance(
            dao: AlbumDao,
            remoteDataSource: RemoteDataSource
        ) =
            instance ?: synchronized(this) {
                instance
                    ?: AlbumRepository(
                        dao, remoteDataSource
                    ).also { instance = it }
            }
    }
}
