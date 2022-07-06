package com.example.kittygram.presentation.ui.home.adapter

import com.example.kittygram.domain.model.Cat

interface CatActionListener {

    fun onCatDownload(cat: Cat)

    fun onCatLike(cat: Cat)
}
