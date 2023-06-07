package com.news.rickandmortyzara.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun RickAndMortyApp(
    rickAndMortyAppState: RickAndMortyAppState = rememberRickAndMortyAppState()
) {
    RickAndMortyScreen{
        RickAndMortyScaffold(
            rickAndMortyAppState = rickAndMortyAppState
        )
    }
}

@Composable
fun RickAndMortyScreen(content: @Composable () -> Unit) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colorScheme.background) {
        content()
    }
}