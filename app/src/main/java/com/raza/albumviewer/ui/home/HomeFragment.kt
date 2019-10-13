package com.raza.albumviewer.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.elifox.legocatalog.data.injectViewModel
import com.raza.albumviewer.databinding.FragmentHomeBinding
import com.raza.albumviewer.di.Injectable
import javax.inject.Inject
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.observe
import com.raza.albumviewer.R

class HomeFragment : Fragment(), Injectable, SearchView.OnQueryTextListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.home)

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )
        viewModel = injectViewModel(viewModelFactory)

        observeSavedAlbumsList(binding)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        (menu?.findItem(R.id.menu_search)?.actionView as SearchView).apply {
            onActionViewExpanded()
            setOnQueryTextListener(this@HomeFragment)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            if (it.isNotEmpty()) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSearchResultsFragment(
                        it
                    )
                Navigation.findNavController(activity!!, R.id.myNavHost).navigate(action)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun observeSavedAlbumsList(binding: FragmentHomeBinding) {
        val adapter = SavedAlbumAdapter {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    "",
                    "",
                    it.id
                )
            Navigation.findNavController(activity!!, R.id.myNavHost).navigate(action)
        }
        binding.savedAlbums?.adapter = adapter
        viewModel.savedAlbums.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
