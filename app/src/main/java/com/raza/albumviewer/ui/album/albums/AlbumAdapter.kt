package com.raza.albumviewer.ui.album.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.databinding.AlbumItemBinding

class AlbumAdapter(val clickEvent: (Album) -> Unit) : PagedListAdapter<Album, AlbumAdapter.ViewHolder> (DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        holder.apply {
            bind(createOnClickListener(album!!), album)
            itemView.tag = album
        }
    }

    private fun createOnClickListener(item: Album): View.OnClickListener {
        return View.OnClickListener {
            clickEvent(item)
        }
    }

    class ViewHolder(
        private val binding: AlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Album) {
            binding.apply {
                clickListener = listener
                album = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}