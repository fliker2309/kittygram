package com.example.kittygram.domain.usecase

data class CatsUseCases(
    val getCats: GetCats,
    val getCatById: GetCatById,
    val saveCatToFavorite: SaveCat,
    val removeCatFromFavorite: RemoveCat,
    val getCatsFromNetwork: GetCatsFromNetwork
)
