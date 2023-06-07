package com.news.rickandmortyzara.ui.presentation.characters.charactersList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.news.rick_and_morty_zara.R
import com.news.rickandmortyzara.ui.presentation.characters.CharactersState
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersList(
    state: CharactersState,
    onClick: (Int) -> Unit,
    onFavoriteEvent: (FavoritesEvent) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 72.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.characters) { character ->
            CharacterItem(
                character = character,
                favorite = state.favoritesCharactersIds.contains(character.id),
                modifier = Modifier.animateItemPlacement(),
                onClick = onClick,
                onFavoriteClick = { characterId ->
                    onFavoriteEvent(FavoritesEvent.AddOrRemoveFavoriteCharacter(characterId))
                }
            )
        }
    }
}

@Composable
fun SuggestionsList(
    charactersSuggestions: Array<String>,
    onClick:(String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(charactersSuggestions) { character ->
            val (name, description) = character.split(":")
            ListItem(
                headlineContent = { Text(name) },
                supportingContent = { Text(description) },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = characterDrawable(name)),
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    onClick(name)
                }
            )
        }
    }
}


private fun characterDrawable(name: String): Int {
    return when (name) {
        "Rick Sanchez" -> R.drawable.rick_sanchez
        "Morty Smith" -> R.drawable.morty_smith
        "Summer Smith" -> R.drawable.summer_smith
        "Beth Smith" -> R.drawable.beth_smith
        "Jerry Smith" -> R.drawable.jerry_smith
        else -> R.drawable.rick_sanchez
    }
}