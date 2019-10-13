package com.raza.albumviewer.repository.artist

import androidx.paging.PageKeyedDataSource
import com.raza.albumviewer.base.BaseResult
import com.raza.albumviewer.data.artist.Artist
import com.raza.albumviewer.datasource.network.RemoteDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for lego sets pagination via paging library
 */
class ArtistPageDataSource @Inject constructor(
        private val params: HashMap<String, Any>? = null,
        private val dataSource: RemoteDataSource,
        private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Artist>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Artist>) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Artist>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.searchArtist(page, pageSize, params)
            if (response.status == BaseResult.Status.SUCCESS) {
                val results = response.data!!.results?.artistmatches?.artist
                // If you want to save network response in database
//                dao.insertAll(results)
                callback(results)
            } else if (response.status == BaseResult.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
    }

}