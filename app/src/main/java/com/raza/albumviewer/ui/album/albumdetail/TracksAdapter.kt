package com.raza.albumviewer.ui.album.albumdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raza.albumviewer.data.album.Album
import com.raza.albumviewer.data.network.AlbumDetailResponse
import com.raza.albumviewer.databinding.TrackItemBinding

class TracksAdapter(val clickEvent: (AlbumDetailResponse.AlbumItem.Tracks.Track) -> Unit) : ListAdapter<AlbumDetailResponse.AlbumItem.Tracks.Track, TracksAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TrackItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        holder.apply {
            bind(createOnClickListener(album!!), album)
            itemView.tag = album
        }
    }

    private fun createOnClickListener(item: AlbumDetailResponse.AlbumItem.Tracks.Track): View.OnClickListener {
        return View.OnClickListener {
            clickEvent(item)
        }
    }

    class ViewHolder(
        private val binding: TrackItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: AlbumDetailResponse.AlbumItem.Tracks.Track) {
            binding.apply {
                clickListener = listener
                track = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<AlbumDetailResponse.AlbumItem.Tracks.Track>() {

    override fun areItemsTheSame(oldItem: AlbumDetailResponse.AlbumItem.Tracks.Track, newItem: AlbumDetailResponse.AlbumItem.Tracks.Track): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: AlbumDetailResponse.AlbumItem.Tracks.Track, newItem: AlbumDetailResponse.AlbumItem.Tracks.Track): Boolean {
        return oldItem == newItem
    }
}