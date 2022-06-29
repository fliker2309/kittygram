package com.example.kittygram.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kittygram.data.model.CatsResponse
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel : ViewModel() {

    private val _allCats = MutableLiveData<CatsResponse>()
    val allCats: LiveData<CatsResponse>
        get() = _allCats
}
