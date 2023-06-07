package com.news.rickandmortyzara.ui.presentation.detail

import com.news.domain.model.CharacterDetail

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val character: CharacterDetail? = null,
    val errorMessage: String? = "",
    val favoritesCharactersIds: List<Int> = emptyList(),
)
