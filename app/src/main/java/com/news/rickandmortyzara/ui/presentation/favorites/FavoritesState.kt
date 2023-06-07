package com.news.rickandmortyzara.ui.presentation.favorites

import com.news.domain.model.ApiError
import com.news.domain.model.Character

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<Character> = emptyList(),
    val favoritesCharactersIds: List<Int> = emptyList(),
    val apiError: ApiError? = null
)