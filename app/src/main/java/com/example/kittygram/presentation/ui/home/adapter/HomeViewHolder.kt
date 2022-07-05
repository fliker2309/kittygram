package com.example.kittygram.presentation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.kittygram.R
import com.example.kittygram.data.model.Cat
import com.example.kittygram.databinding.CatItemBinding

class HomeViewHolder(val binding: CatItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cat: Cat) {
        binding.apply {
            catImage.load(cat.url) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
            }
            profileImage.load(cat.url) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
                transformations(RoundedCornersTransformation(20f))
            }
            profileNameTV.text = cat.id
        }
    }

    object HomeFragmentDiffItemCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.url == newItem.url
        }
    }
}
