package com.famgy.everything.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.famgy.everything.databinding.ListItemSongBinding
import com.famgy.everything.model.bean.Song

class SongAdapter : ListAdapter<Song, RecyclerView.ViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SongViewHolder(
            ListItemSongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = getItem(position)
        (holder as SongViewHolder).bind(song)
    }

    class SongViewHolder(
        private val binding: ListItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
        }

        fun bind(item: Song) {
            binding.apply {
                song = item
                executePendingBindings()
            }
        }
    }
}

private class SongDiffCallback : DiffUtil.ItemCallback<Song>() {

    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }
}