package com.raza.albumviewer.ui.home

import androidx.lifecycle.ViewModel
import com.raza.albumviewer.di.CoroutineScropeIO
import com.raza.albumviewer.repository.album.AlbumRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel used in [LegoSetFragment].
 */
class HomeViewModel @Inject constructor(private val albumRepository: AlbumRepository,
                                        @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope)
    : ViewModel() {

    private var albumId: Int? = null

    val savedAlbums by lazy {
        albumRepository.loadSavedAlbums()
    }

    val albumDetails by lazy {
        albumId?.let {
            albumRepository.loadAlbumDetails(it)
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
