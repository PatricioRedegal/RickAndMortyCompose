package com.news.rickandmortyzara.ui.presentation.characters

sealed interface CharactersEvent {
    data class UpdateSearchText(val searchText: String): CharactersEvent
    data class FilterStatus(val status: String): CharactersEvent
}