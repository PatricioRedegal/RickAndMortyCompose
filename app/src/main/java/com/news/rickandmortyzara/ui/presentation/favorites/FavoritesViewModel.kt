package com.news.rickandmortyzara.ui.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.domain.util.isSuccessful
import com.news.rickandmortyzara.datastore.FavoritesStore
import com.news.rickandmortyzara.ui.presentation.characters.CharactersState
import com.news.usecases.characters.GetFavoritesCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesCharactersUseCase: GetFavoritesCharactersUseCase,
    private val favoritesStore: FavoritesStore
): ViewModel() {
    private val favoritesCharactersIds = favoritesStore.getFavoriteIds()

    private val _state = MutableStateFlow(FavoritesState())
    val state = combine(
        _state,
        favoritesCharactersIds
    ) { state, favoritesCharactersIds ->
        state.copy(
            favoritesCharactersIds = favoritesCharactersIds
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoritesState())

    init {
        viewModelScope.launch {
            getFavorites()
        }
    }
    suspend fun getFavorites() {
        _state.value = _state.value.copy(isLoading = true)

        val ids = favoritesStore.getFavoriteIds().first()

        viewModelScope.launch {
            val response = getFavoritesCharactersUseCase(ids)
            when {
                response.status.isSuccessful() -> {
                    _state.value = _state.value.copy(
                        favorites = response.data ?: emptyList(),
                        apiError = null,
                        isLoading = false
                    )
                }
                else -> {
                    _state.value = _state.value.copy(
                        favorites = emptyList(),
                        apiError = response.apiError,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: FavoritesEvent) {
        when(event) {
            is FavoritesEvent.AddOrRemoveFavoriteCharacter -> {
                viewModelScope.launch {
                    val ids = favoritesStore.getFavoriteIds().first()
                    when {
                        ids.contains(event.id) -> favoritesStore.removeFromFavoriteIds(event.id)
                        else -> favoritesStore.addToFavoriteIds(event.id)
                    }
                    getFavorites()
                }
            }
        }
    }
}