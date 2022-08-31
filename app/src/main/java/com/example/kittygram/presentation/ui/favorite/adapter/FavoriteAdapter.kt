package com.example.kittygram.presentation.ui.favorite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kittygram.domain.model.Cat
import com.example.kittygram.presentation.ui.favorite.EventDispatcher

class FavoriteAdapter(
    private val eventDispatcher: EventDispatcher
) : ListAdapter<Cat, FavoriteViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
      return FavoriteViewHolder.create(parent,eventDispatcher)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)
    }
}