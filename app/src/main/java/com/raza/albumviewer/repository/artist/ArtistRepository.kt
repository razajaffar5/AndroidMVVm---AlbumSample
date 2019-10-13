package com.raza.albumviewer.repository.artist

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raza.albumviewer.di.OpenForTesting
import com.raza.albumviewer.data.artist.Artist
import com.raza.albumviewer.datasource.network.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
@OpenForTesting
class ArtistRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun observePagedSets(connectivityAvailable: Boolean, params: HashMap<String, Any>,
                         coroutineScope: CoroutineScope) =  observeRemotePagedSets(params, coroutineScope)


/*
In order to get data from database Dao object is used to retrieve data
        if (connectivityAvailable)

        else {
            observeLocalPagedSets(params[ARTIST_PARAMM] as String)
        }

    private fun observeLocalPagedSets(keyword: String): LiveData<PagedList<Artist>> {
        val dataSourceFactory = dao.searchArtisttPaged(keyword)

        return LivePagedListBuilder(dataSourceFactory,
            ArtistPageDataSourceFactory.pagedListConfig()
        ).build()
    }
*/

    private fun observeRemotePagedSets(params: HashMap<String, Any>?, ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Artist>> {
        val dataSourceFactory = ArtistPageDataSourceFactory(
            params, remoteDataSource, ioCoroutineScope
        )

        return LivePagedListBuilder(dataSourceFactory,
            ArtistPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ArtistRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: ArtistRepository(
                        remoteDataSource
                    ).also { instance = it }
            }
    }
}
