package com.example.kittygram.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kittygram.data.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel : ViewModel() {

    private val _allCats = MutableLiveData<Cat>()
    val allCats: LiveData<Cat>
        get() = _allCats
}
