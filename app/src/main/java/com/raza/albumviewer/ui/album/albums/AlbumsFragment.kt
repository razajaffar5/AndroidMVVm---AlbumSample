package com.raza.albumviewer.ui.album.albums
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.elifox.legocatalog.data.injectViewModel
import com.raza.albumviewer.R
import com.raza.albumviewer.databinding.FragmentAlbumsBinding
import com.raza.albumviewer.di.Injectable
import com.raza.albumviewer.ui.album.AlbumViewModel
import com.raza.albumviewer.util.prefs.*
import javax.inject.Inject

class AlbumsFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AlbumViewModel

    private val arguments: AlbumsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.albums)

        viewModel = injectViewModel(viewModelFactory)

        val binding = DataBindingUtil.inflate<FragmentAlbumsBinding>(
            inflater, R.layout.fragment_albums, container, false
        )

        val artist = arguments.artist

        observeAlbumList(binding, artist)

        return binding.root
    }

    private fun observeAlbumList(binding: FragmentAlbumsBinding, artist: String) {
        val params = HashMap<String, Any>()
        params[METHOD_PARAMM] = TOP_ALBUMS_ENDPOINT
        params[FORMAT_PARAM] = FORMAT_VALUE
        params[ARTIST_PARAMM] = artist
        viewModel.params = params

        val adapter = AlbumAdapter {
            val action =
                AlbumsFragmentDirections.actionAlbumsFragmentToDetailFragment(
                    artist,
                    it.name,
                    -1
                )
            Navigation.findNavController(activity!!, R.id.myNavHost).navigate(action)
        }
        binding.albumItems?.adapter = adapter
        viewModel.topAlbums
        viewModel.topAlbums?.observe(this, Observer { result ->
            adapter.submitList(result)
        })
    }
}
