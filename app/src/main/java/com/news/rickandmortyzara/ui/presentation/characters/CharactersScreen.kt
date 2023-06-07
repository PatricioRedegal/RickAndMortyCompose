package com.news.rickandmortyzara.ui.presentation.characters

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.news.rick_and_morty_zara.R
import com.news.rickandmortyzara.network.ConnectionState
import com.news.rickandmortyzara.network.ConnectivityStatus
import com.news.rickandmortyzara.network.connectivityState
import com.news.rickandmortyzara.ui.common.SimpleCircularProgressComponent
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.CharactersList
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.SuggestionsList
import com.news.rickandmortyzara.ui.presentation.characters.filters.DropdownFilters
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun CharactersScreen(
    state: CharactersState,
    onClick: (Int) -> Unit,
    onEvent: (CharactersEvent) -> Unit,
    onFavoriteEvent: (FavoritesEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val charactersSuggestions = stringArrayResource(id = R.array.characters_suggestions)

    BackHandler {
        when {
            state.searchText.isNotEmpty() -> onEvent(CharactersEvent.UpdateSearchText(""))
            else -> {
                coroutineScope.launch {
                    (context as? Activity)?.finish()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var active by remember { mutableStateOf(false) }
        var showFilters by remember { mutableStateOf(false) }

        ConnectivityStatus(isConnected = isConnected)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .semantics { isContainer = true }
                    .zIndex(1f)
                    .fillMaxWidth()
            ) {
                DockedSearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp),
                    query = state.searchText,
                    onQueryChange = { onEvent(CharactersEvent.UpdateSearchText(it)) },
                    onSearch = {
                        onEvent(CharactersEvent.UpdateSearchText(it))
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(stringResource(R.string.name_of_the_character)) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        Box {
                            BadgedBox(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable(
                                        onClick = {
                                            showFilters = true
                                        }
                                    ),
                                badge = {
                                    Badge(
                                        containerColor = when {
                                            state.filterStatus.isNotEmpty() -> {
                                                MaterialTheme.colorScheme.primary
                                            }
                                            else -> Color.Transparent
                                        }
                                    ) {
                                        when {
                                            state.filterStatus.isNotEmpty() -> {
                                                Text(
                                                    text = "1",
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                            }
                            DropdownFilters(
                                expanded = showFilters,
                                filterStatus = state.filterStatus,
                                onClick = { status ->
                                    onEvent(CharactersEvent.FilterStatus(status))
                                },
                                onDismiss = {
                                    showFilters = false
                                }
                            )
                        }
                    },
                ) {
                    SuggestionsList(charactersSuggestions, onClick = { name ->
                        onEvent(CharactersEvent.UpdateSearchText(name))
                        active = false
                    })
                }
            }

            when {
                state.characters.isEmpty() -> {
                    when {
                        isConnected -> {
                            when {
                                state.apiError != null -> NoResultsBackground()
                                else -> MainBackground(modifier = Modifier.align(Alignment.Center).fillMaxSize())
                            }
                        }
                        else -> NoInternetBackground()
                    }
                }
                else -> CharactersList(
                    state = state,
                    onClick = onClick,
                    onFavoriteEvent = onFavoriteEvent
                )
            }
        }
    }

    if(state.isLoading){
        SimpleCircularProgressComponent()
    }
}

@Composable
private fun MainBackground(
    modifier: Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.rick_background),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun NoInternetBackground() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorites_empty))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
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

@Composable
fun NoResultsBackground() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_results))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        val transition = rememberInfiniteTransition()

        val scale by transition.animateFloat(
            initialValue = 1f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp)
                .scale(scale),
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
