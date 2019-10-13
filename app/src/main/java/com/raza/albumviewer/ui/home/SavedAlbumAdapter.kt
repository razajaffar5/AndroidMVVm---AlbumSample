package com.raza.albumviewer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.network.AlbumDetailResponse
import com.raza.albumviewer.databinding.AlbumItemBinding
import com.raza.albumviewer.databinding.SavedAlbumItemBinding

class SavedAlbumAdapter(val clickEvent: (AlbumDetailResponse.AlbumItem) -> Unit) : ListAdapter<AlbumDetailResponse.AlbumItem, SavedAlbumAdapter.ViewHolder> (DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SavedAlbumItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        holder.apply {
            bind(createOnClickListener(album!!), album)
            itemView.tag = album
        }
    }

    private fun createOnClickListener(item: AlbumDetailResponse.AlbumItem): View.OnClickListener {
        return View.OnClickListener {
            clickEvent(item)
        }
    }

    class ViewHolder(
        private val binding: SavedAlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: AlbumDetailResponse.AlbumItem) {
            binding.apply {
                clickListener = listener
                album = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<AlbumDetailResponse.AlbumItem>() {

    override fun areItemsTheSame(oldItem: AlbumDetailResponse.AlbumItem, newItem: AlbumDetailResponse.AlbumItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlbumDetailResponse.AlbumItem, newItem: AlbumDetailResponse.AlbumItem): Boolean {
        return oldItem == newItem
    }
}