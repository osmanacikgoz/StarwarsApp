package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.FilmRowBinding
import com.example.starwarsapp.model.FilmResponse

class FilmAdapter : ListAdapter<FilmResponse, FilmAdapter.FilmViewHolder>(CHARACTER_COMPARATOR) {
    inner class FilmViewHolder(private val binding: FilmRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filmResponse: FilmResponse?) {
            binding.apply {
                itemTitle.text = filmResponse?.title
                itemDescription.text = filmResponse?.openingCrawl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.FilmViewHolder {
        return FilmViewHolder(
            FilmRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmAdapter.FilmViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }


    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<FilmResponse>() {
            override fun areItemsTheSame(oldItem: FilmResponse, newItem: FilmResponse): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: FilmResponse, newItem: FilmResponse): Boolean {
                return oldItem == newItem
            }

        }
    }
}