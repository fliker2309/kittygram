package com.example.kittygram.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittygram.data.model.CatsResponse
import com.example.kittygram.data.network.TestRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TestRepo) : ViewModel() {

    private val _allCats = MutableLiveData<CatsResponse>()
    val allCats: LiveData<CatsResponse>
        get() = _allCats

    init {
        getAll()
    }

    fun getAll() = viewModelScope.launch {
        repository.getCats().let {
            if (it.isSuccessful) {
                _allCats.postValue(it.body())
            } else {
                Log.d("tag", "Failed to load cats ${it.errorBody()}")
            }
        }
    }
}
