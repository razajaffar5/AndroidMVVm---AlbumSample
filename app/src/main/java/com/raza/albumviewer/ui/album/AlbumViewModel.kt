package com.raza.albumviewer.ui.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raza.albumviewer.base.BaseResult
import com.raza.albumviewer.data.network.AlbumDetailResponse
import com.raza.albumviewer.di.CoroutineScropeIO
import com.raza.albumviewer.repository.album.AlbumRepository
import com.raza.albumviewer.repository.artist.ArtistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [AlbumsFragment and DetailFragment].
 */
class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val repository: ArtistRepository,
    @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = true
    var params: HashMap<String, Any>? = null
    var artist: String? = null
    var album: String? = null
    var id: Int? = null

    var albumDetailItem = MutableLiveData<BaseResult<AlbumDetailResponse>>()

    val topAlbums by lazy {
        params?.let {
            albumRepository.observePagedSets(connectivityAvailable, it, ioCoroutineScope)
        }
    }

    val albumDetailsFromDb by lazy {
        id?.let {
            albumRepository.loadAlbumDetails(it)
        }
    }

    val albumDetailsByName by lazy {
        album?.let {
            albumRepository.loadAlbumDetailsByName(it, artist!!)
        }
    }


    fun loadAlbumDetail() {
        ioCoroutineScope.launch {
            params?.let {
                albumDetailItem.postValue(albumRepository.observeAlbumDetail(it))
            }
        }
    }

    fun saveAlbumItem(item: AlbumDetailResponse.AlbumItem) {
        ioCoroutineScope.launch {
            albumRepository.saveAlbum(item, ioCoroutineScope)
        }
    }

    fun deleteAlbumItem(item: AlbumDetailResponse.AlbumItem) {
        ioCoroutineScope.launch {
            albumRepository.deleteAlbum(item, ioCoroutineScope)
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
