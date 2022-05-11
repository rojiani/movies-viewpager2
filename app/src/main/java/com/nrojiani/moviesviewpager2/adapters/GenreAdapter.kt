package com.nrojiani.moviesviewpager2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.databinding.GenreItemBinding
import timber.log.Timber

class GenreAdapter(private val onItemClick: (genre: Genre) -> Unit) :
    ListAdapter<Genre, GenreAdapter.ViewHolder>(GenreDiffCallback()) {

    class ViewHolder private constructor(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre, clickListener: (genre: Genre) -> Unit) {
            binding.genreNameText.text = genre.displayName()
            binding.genreNameCard.setOnClickListener {
                Timber.d("$genre clicked")
                clickListener(genre)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: GenreItemBinding = GenreItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, onItemClick)
    }
}

class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem == newItem
}
