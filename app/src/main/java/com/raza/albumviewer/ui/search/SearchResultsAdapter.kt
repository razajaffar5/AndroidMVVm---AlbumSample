package com.raza.albumviewer.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raza.albumviewer.data.artist.Artist
import com.raza.albumviewer.databinding.ArtistItemBinding

class SearchResultsAdapter(val clickEvent: (Artist) -> Unit) : PagedListAdapter<Artist, SearchResultsAdapter.ViewHolder> (DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ArtistItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = getItem(position)
        holder.apply {
            bind(createOnClickListener(artist!!), artist)
            itemView.tag = artist
        }
    }

    private fun createOnClickListener(item: Artist): View.OnClickListener {
        return View.OnClickListener {
            clickEvent(item)
        }
    }

    class ViewHolder(
        private val binding: ArtistItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Artist) {
            binding.apply {
                clickListener = listener
                artist = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.mbid == newItem.mbid
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}