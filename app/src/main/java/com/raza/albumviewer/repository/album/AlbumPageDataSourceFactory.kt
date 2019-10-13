package com.raza.albumviewer.repository.album

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.album.AlbumDao
import com.raza.albumviewer.datasource.network.RemoteDataSource
import com.raza.albumviewer.util.prefs.PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AlbumPageDataSourceFactory @Inject constructor(
        private val params: HashMap<String, Any>? = null,
        private val dataSource: RemoteDataSource,
        private val dao: AlbumDao,
        private val scope: CoroutineScope
) : DataSource.Factory<Int, Album>() {

    private val liveData = MutableLiveData<AlbumPageDataSource>()

    override fun create(): DataSource<Int, Album> {
        val source = AlbumPageDataSource(
            params,
            dataSource,
            dao,
            scope
        )
        liveData.postValue(source)
        return source
    }

    companion object {

        fun pagedListConfig() = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(true)
                .build()
    }

}
