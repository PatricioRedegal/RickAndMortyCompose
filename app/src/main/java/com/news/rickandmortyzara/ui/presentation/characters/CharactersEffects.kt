package com.news.rickandmortyzara.ui.presentation.characters

import com.news.domain.model.ApiError

sealed interface CharactersEffects {
    data class Error(val apiError: ApiError?) : CharactersEffects
}