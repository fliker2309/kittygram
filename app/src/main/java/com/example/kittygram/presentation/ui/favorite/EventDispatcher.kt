package com.example.kittygram.presentation.ui.favorite

import android.view.View
import com.example.kittygram.domain.model.Cat

interface EventDispatcher {
    fun onCatPressed(cat: Cat, view: View)
}