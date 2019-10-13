package com.raza.albumviewer.repository.artist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.raza.albumviewer.data.artist.Artist
import com.raza.albumviewer.datasource.network.RemoteDataSource
import com.raza.albumviewer.util.prefs.PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ArtistPageDataSourceFactory @Inject constructor(
        private val params: HashMap<String, Any>? = null,
        private val dataSource: RemoteDataSource,
        private val scope: CoroutineScope
) : DataSource.Factory<Int, Artist>() {

    private val liveData = MutableLiveData<ArtistPageDataSource>()

    override fun create(): DataSource<Int, Artist> {
        val source = ArtistPageDataSource(
            params,
            dataSource,
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
