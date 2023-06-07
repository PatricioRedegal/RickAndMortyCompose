package com.news.rickandmortyzara.ui.presentation.characters

import com.news.domain.model.ApiError
import com.news.domain.model.Character

data class CharactersState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val characters: List<Character> = emptyList(),
    val favoritesCharactersIds: List<Int> = emptyList(),
    val filterStatus: String = "",
    val apiError: ApiError? = null
)