package com.news.rickandmortyzara.ui.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.domain.repository.CharacterDetailRepository
import com.news.rickandmortyzara.datastore.FavoritesStore
import com.news.rickandmortyzara.ui.navigation.NavArg
import com.news.rickandmortyzara.ui.presentation.characters.CharactersState
import com.news.usecases.characterDetail.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUsecase: GetCharacterDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val repository: CharacterDetailRepository,
    favoritesStore: FavoritesStore
) : ViewModel() {
    private val characterId = savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0
    private val favoritesCharactersIds = favoritesStore.getFavoriteIds()

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state = combine(
        _state,
        favoritesCharactersIds
    ) { state, favoritesCharactersIds ->
        state.copy(
            favoritesCharactersIds = favoritesCharactersIds
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterDetailsState())

    init {
        viewModelScope.launch {
            _state.value = CharacterDetailsState(isLoading = true)
            _state.value = CharacterDetailsState(character = repository.getCharacterDetail(characterId).data)
        }
    }
}