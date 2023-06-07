package com.news.rickandmortyzara.ui.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.domain.util.isSuccessful
import com.news.rickandmortyzara.datastore.CharactersFiltersStore
import com.news.rickandmortyzara.datastore.FavoritesStore
import com.news.usecases.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val charactersFiltersStore: CharactersFiltersStore,
    favoritesStore: FavoritesStore
): ViewModel() {
    private val favoritesCharactersIds = favoritesStore.getFavoriteIds()
    private val filterStatus = charactersFiltersStore.filterStatus

    private val _state = MutableStateFlow(CharactersState())
    val state = combine(
        _state,
        filterStatus,
        favoritesCharactersIds
    ) { state, filterStatus, favoritesCharactersIds ->
        state.copy(
            filterStatus = filterStatus,
            favoritesCharactersIds = favoritesCharactersIds
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharactersState())

    var effects = MutableSharedFlow<CharactersEffects>()
        private set

    fun getCharacters() {
        if(_state.value.searchText.isBlank()){
            _state.value = _state.value.copy(characters = emptyList(), apiError = null)
            return
        }

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            val response = getCharactersUseCase(
                name = state.value.searchText,
                status = state.value.filterStatus
            )
            when {
                response.status.isSuccessful() -> {
                    _state.value = _state.value.copy(
                        characters = response.data ?: emptyList(),
                        apiError = null,
                        isLoading = false
                    )
                }
                else -> {
                    _state.value = _state.value.copy(
                        characters = emptyList(),
                        apiError = response.apiError,
                        isLoading = false
                    )

                    //If we want to send a message to the charactersScreen, for example, to display a Toast, we would execute the following code.
                    /*if(response.apiError?.code != 404)
                        effects.emit(Effect.Error(response.apiError))*/
                }
            }
        }
    }

    fun onEvent(event: CharactersEvent) {
        when(event) {
            is CharactersEvent.UpdateSearchText ->  {
                _state.value = _state.value.copy(searchText = event.searchText)
                getCharacters()
            }
            is CharactersEvent.FilterStatus -> {
                viewModelScope.launch {
                    charactersFiltersStore.setFilterStatus(event.status)
                    getCharacters()
                }
            }
        }
    }
}