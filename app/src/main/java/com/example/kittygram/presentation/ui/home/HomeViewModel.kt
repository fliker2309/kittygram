package com.example.kittygram.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kittygram.data.network.CatsPagingSource
import com.example.kittygram.data.network.CatsService
import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.usecase.CatsUseCases
import com.example.kittygram.utils.Constants.Companion.DEFAULT_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catsUseCases: CatsUseCases,
    private val service: CatsService
) : ViewModel() {

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
    // todo read how to get cat from usecase
    /*  fun getAllCats() {
          viewModelScope.launch {
              _allCats.value = catsUseCases.getCatsFromNetwork()
          }
      }*/

    val flow = Pager(PagingConfig(DEFAULT_PAGE_SIZE)) {
        CatsPagingSource(service)
    }.flow.cachedIn(viewModelScope)
}
