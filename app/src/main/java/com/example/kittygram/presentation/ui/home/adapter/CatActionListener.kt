package com.example.kittygram.presentation.ui.home.adapter

import com.example.kittygram.data.model.Cat

interface CatActionListener {

    fun onCatDownload(cat: Cat)

    fun onCatLike(cat: Cat)
}
