package com.example.kittygram.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kittygram.R
import com.example.kittygram.databinding.CatItemBinding
import com.example.kittygram.domain.model.Cat

class HomeAdapter(
    private val actionListener: CatActionListener
) :
    PagingDataAdapter<Cat, HomeViewHolder>(HomeViewHolder.HomeFragmentDiffItemCallback),
    View.OnClickListener {
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            likeBtn.tag = item
            downloadBtn.tag = item
        }
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(inflater, parent, false)

        binding.likeBtn.setOnClickListener(this)
        binding.downloadBtn.setOnClickListener(this)

        return HomeViewHolder(binding)
    }

    override fun onClick(v: View) {
        val cat = v.tag as Cat
        when (v.id) {
            R.id.like_btn -> {
                cat.isLiked = !cat.isLiked
                actionListener.onCatLike(cat)
                v.setBackgroundResource(if (cat.isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline) //вынести во фрагмент
            }
            R.id.download_btn -> {
                actionListener.onCatDownload(cat)
            }
        }
    }
}
