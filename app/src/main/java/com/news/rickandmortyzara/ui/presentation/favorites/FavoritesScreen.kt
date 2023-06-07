package com.news.rickandmortyzara.ui.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.news.rick_and_morty_zara.R
import com.news.rickandmortyzara.ui.presentation.favorites.favoritesList.FavoritesList

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onEvent: (FavoritesEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        if(state.favoritesCharactersIds.isEmpty()) {
            NoFavoritesBackground()
        }
        else{
            FavoritesList(
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun NoFavoritesBackground() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.morty_cry))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}