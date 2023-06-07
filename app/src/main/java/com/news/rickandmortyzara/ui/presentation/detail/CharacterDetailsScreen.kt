package com.news.rickandmortyzara.ui.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.news.rick_and_morty_zara.R
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.FavoriteButton
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.StatusColorCircle
import com.news.rickandmortyzara.ui.presentation.favorites.FavoritesEvent
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    detailsState: CharacterDetailsState,
    onFavoriteEvent: (FavoritesEvent) -> Unit,
    onBack: () -> Unit
) {
    val character = detailsState.character ?: return
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val topAppBarElementColor = MaterialTheme.colorScheme.onSurface

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Icon(painter = painterResource(R.drawable.hbo), contentDescription = null) },
            text = { Text(text = stringResource(R.string.access_in_hbo)) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageIcon(
                            modifier = Modifier.size(56.dp),
                            imageUrl = character.image
                        )
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = character.name,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusColorCircle(status = character.status)
                                Text(
                                    modifier = Modifier.padding(start = 4.dp),
                                    text = character.status,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    FavoriteButton(
                        onClick = {
                            onFavoriteEvent(FavoritesEvent.AddOrRemoveFavoriteCharacter(character.id))
                        },
                        favorite = detailsState.favoritesCharactersIds.contains(character.id)
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor= topAppBarElementColor,
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            InfoRow(label = "${stringResource(R.string.species)}:", value = character.species)
            InfoRow(label = "${stringResource(R.string.gender)}:", value = character.gender)
            InfoRow(label = "${stringResource(R.string.origin)}:", value = character.origin)
            InfoRow(label = "${stringResource(R.string.location)}:", value = character.location)
            InfoRow(label = "${stringResource(R.string.episodes)}:", value = character.episode.size.toString())
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            character.episode.forEach { episode ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            showDialog = true
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${stringResource(R.string.episode)} ${episode.substringAfterLast("/")}"
                    )
                }
            }
        }
    }
}

@Composable
fun ImageIcon(
    imageUrl: String,
    modifier: Modifier
) {
    CoilImage(
        modifier = modifier.clip(CircleShape),
        imageRequest =
        ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        alignment = Alignment.Center,
        circularReveal = CircularReveal(
            duration = 1000,
        )
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium)
        )
    }
}
