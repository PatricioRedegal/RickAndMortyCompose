package com.news.rickandmortyzara.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.news.rickandmortyzara.ui.navigation.AppBottomNavigation
import com.news.rickandmortyzara.ui.navigation.Navigation

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun RickAndMortyScaffold(
    rickAndMortyAppState: RickAndMortyAppState
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = rickAndMortyAppState.showBottomNavigation,
                exit = fadeOut(),
                enter = slideInVertically(initialOffsetY = { it })
            ) {
                AppBottomNavigation(
                    bottomNavOptions = RickAndMortyAppState.BOTTOM_NAV_OPTIONS,
                    currentRoute = rickAndMortyAppState.currentRoute,
                    onNavItemClick = { rickAndMortyAppState.onNavItemClick(it) }
                )
            }
        },
        scaffoldState = rickAndMortyAppState.scaffoldState
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding))
        {
            Column {
                Navigation(
                    rickAndMortyAppState = rickAndMortyAppState
                )
            }
        }
    }
}


