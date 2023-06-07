package com.news.rickandmortyzara.ui.presentation.favorites.favoritesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.news.rickandmortyzara.ui.presentation.characters.CharactersEvent
import com.news.rickandmortyzara.ui.presentation.characters.CharactersState
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.CharacterItem
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesEvent
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesList(
    state: FavoritesState,
    onEvent: (FavoritesEvent) -> Unit)
{
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.favorites) { character ->
            CharacterItem(
                character = character,
                favorite = true,
                modifier = Modifier.animateItemPlacement(),
                onClick = {
                    //onEvent(CharactersEvent.SelectCharacter(id))
                },
                onFavoriteClick = {
                    onEvent(FavoritesEvent.AddOrRemoveFavoriteCharacter(it))
                }
            )
        }
    }
}