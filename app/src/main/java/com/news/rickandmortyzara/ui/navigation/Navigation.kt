package com.news.rickandmortyzara.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.news.rickandmortyzara.ui.RickAndMortyAppState
import com.news.rickandmortyzara.ui.presentation.characters.CharactersEffects
import com.news.rickandmortyzara.ui.presentation.characters.CharactersScreen
import com.news.rickandmortyzara.ui.presentation.characters.CharactersViewModel
import com.news.rickandmortyzara.ui.presentation.detail.CharacterDetailsScreen
import com.news.rickandmortyzara.ui.presentation.detail.CharacterDetailsViewModel
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesScreen
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    rickAndMortyAppState: RickAndMortyAppState
) {
    NavHost(
        navController = rickAndMortyAppState.navController,
        startDestination = Feature.CHARACTERS.route
    ) {
        charactersNav(rickAndMortyAppState.navController)
        favoritesNav()
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.charactersNav(navController: NavController)
{
    navigation(
        startDestination = NavCommand.ContentType(Feature.CHARACTERS).route,
        route = Feature.CHARACTERS.route
    ) {
        composable(
            navCommand = NavCommand.ContentType(Feature.CHARACTERS)
        ) { navBackStackEntry ->
            val charactersViewModel = hiltViewModel<CharactersViewModel>()
            val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
            val state by charactersViewModel.state.collectAsStateWithLifecycle()

            val context = LocalContext.current
            LaunchedEffect(charactersViewModel.effects) {
                charactersViewModel.effects.collect { effects ->
                    when (effects) {
                        is CharactersEffects.Error -> Toast.makeText(context, effects.apiError?.code.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            CharactersScreen(
                state = state,
                onEvent = charactersViewModel::onEvent,
                onFavoriteEvent = favoritesViewModel::onEvent,
                onClick = { characterId ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHARACTERS).createRoute(characterId)
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.CHARACTERS)) {
            val characterDetailsViewModel = hiltViewModel<CharacterDetailsViewModel>()
            val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
            val state by characterDetailsViewModel.state.collectAsStateWithLifecycle()

            CharacterDetailsScreen(
                detailsState = state,
                onBack = { navController.popBackStack() },
                onFavoriteEvent = favoritesViewModel::onEvent,
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.favoritesNav() {
    navigation(
        startDestination = NavCommand.ContentType(Feature.FAVORITES).route,
        route = Feature.FAVORITES.route
    ) {
        composable(
            navCommand = NavCommand.ContentType(Feature.FAVORITES)
        ) {
            val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
            val state by favoritesViewModel.state.collectAsStateWithLifecycle()

            FavoritesScreen(
                state = state,
                onEvent = favoritesViewModel::onEvent
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ) {
        content(it)
    }
}
