package com.news.rickandmortyzara.ui.presentation.favorites

sealed interface FavoritesEvent {
    data class AddOrRemoveFavoriteCharacter(val id: Int): FavoritesEvent
}