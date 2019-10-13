package com.raza.albumviewer.ui.search

import androidx.lifecycle.ViewModel
import com.raza.albumviewer.di.CoroutineScropeIO
import com.raza.albumviewer.repository.artist.ArtistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel used in [SearchResultsActivity].
 */
class SearchViewModel @Inject constructor(private val repository: ArtistRepository,
                                          @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
)
    : ViewModel() {

    var connectivityAvailable: Boolean = true
    var params: HashMap<String, Any>? = null

    val searchArtistResult by lazy {
        params?.let {
            repository.observePagedSets(connectivityAvailable, it, ioCoroutineScope)
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
