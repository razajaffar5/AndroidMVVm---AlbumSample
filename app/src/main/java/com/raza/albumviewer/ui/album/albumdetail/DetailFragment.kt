package com.raza.albumviewer.ui.album.albumdetail

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.elifox.legocatalog.data.injectViewModel
import com.raza.albumviewer.R
import com.raza.albumviewer.base.BaseResult
import com.raza.albumviewer.databinding.FragmentDetailBinding
import com.raza.albumviewer.di.Injectable
import com.raza.albumviewer.ui.album.AlbumViewModel
import com.raza.albumviewer.util.prefs.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AlbumViewModel
    private lateinit var tracksAdapter: TracksAdapter

    private val arguments: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.detail)
        viewModel = injectViewModel(viewModelFactory)

        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater, R.layout.fragment_detail, container, false
        )

        val artist = arguments.artist
        val album = arguments.album
        val albumId = arguments.albumId

        tracksAdapter = TracksAdapter {

        }
        binding.tracksList?.adapter = tracksAdapter

        if (albumId == -1) {
            observeRemoteAlbum(binding, artist, album)
        } else {
            observeLocalAlbum(binding, albumId)
        }

        return binding.root
    }

    private fun observeRemoteAlbum(binding: FragmentDetailBinding, artist: String, albumValue: String) {
        val params = HashMap<String, Any>()
        params[METHOD_PARAMM] = ALBUM_INFO_ENDPOINT
        params[FORMAT_PARAM] = FORMAT_VALUE
        params[ARTIST_PARAMM] = artist
        params[ALBUM_PARAMM] = albumValue
        viewModel.params = params
        viewModel.loadAlbumDetail()
        viewModel.albumDetailItem?.observe(this, Observer {
            when (it.status) {
                BaseResult.Status.SUCCESS -> {
                    it?.data?.album?.let { item ->
                        binding.apply {
                            album = item
                            text = getString(R.string.save_album)
                            executePendingBindings()
                        }
                        item?.tracks?.track?.let {
                            tracksAdapter.submitList(it)
                        }
                        binding.save.setOnClickListener {
                            viewModel.saveAlbumItem(item)
                            activity?.onBackPressed()
                        }
                    }
                }
                else -> {
                }
            }
        })

    }

    private fun observeLocalAlbum(binding: FragmentDetailBinding, albumId: Int) {
        viewModel.id = albumId
        viewModel.albumDetailsFromDb?.observe(this, Observer {
            it?.let { item ->
                binding.apply {
                    album = item
                    text = getString(R.string.delete_album)
                    executePendingBindings()
                }
                tracksAdapter.submitList(item?.tracks?.track)
                binding.save.setOnClickListener {
                    viewModel.deleteAlbumItem(item)
                    Handler().postDelayed({
                        activity?.onBackPressed()
                    },500)
                }
            }
        })
    }

}
