package com.raza.albumviewer.ui.search

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
import com.raza.albumviewer.databinding.FragmentSearchResultsBinding
import com.raza.albumviewer.di.Injectable
import com.raza.albumviewer.util.prefs.*
import javax.inject.Inject

class SearchResultsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel

    private val arguments: SearchResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.artist_label)
        setHasOptionsMenu(false)
        viewModel = injectViewModel(viewModelFactory)

        val binding = DataBindingUtil.inflate<FragmentSearchResultsBinding>(
            inflater, R.layout.fragment_search_results, container, false
        )

        val query = arguments.query

        observeArtistList(binding, query)

        return binding.root
    }

    private fun observeArtistList(binding: FragmentSearchResultsBinding, query: String) {
        val params = HashMap<String, Any>()
        params[METHOD_PARAMM] = SEARCH_ARTIST_ENDPOINT
        params[FORMAT_PARAM] = FORMAT_VALUE
        params[ARTIST_PARAMM] = query
        viewModel.params = params

        val adapter = SearchResultsAdapter {
            val action =
                SearchResultsFragmentDirections.actionSearchResultsFragmentToAlbumsFragment(
                    it.name
                )
            Navigation.findNavController(activity!!, R.id.myNavHost).navigate(action)
        }
        binding.artistItems.adapter = adapter
        viewModel.searchArtistResult?.observe(this, Observer { results ->
            adapter.submitList(results)
        })

    }
}
