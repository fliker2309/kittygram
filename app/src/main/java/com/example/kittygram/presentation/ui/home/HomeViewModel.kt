package com.example.kittygram.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.usecase.CatsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val catsUseCases: CatsUseCases) : ViewModel() {

    private val _allCats = MutableLiveData<Cat>()
    val allCats: LiveData<Cat>
        get() = _allCats

     fun saveCatToFavorites(cat: Cat) {
        viewModelScope.launch {
            catsUseCases.saveCatToFavorite(cat)
        }
    }

     fun removeCatFromFavorites(cat: Cat) {
        viewModelScope.launch {
            catsUseCases.removeCatFromFavorite(cat)
        }
    }
    //todo read how to get cat from usecase
  /*  fun getAllCats() {
        viewModelScope.launch {
            _allCats.value = catsUseCases.getCatsFromNetwork()
        }
    }*/
}
