package com.news.rickandmortyzara.ui.presentation.characters.charactersList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.news.domain.model.Character
import coil.request.ImageRequest
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: Character,
    favorite: Boolean = false,
    onClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit)
{
    Card(
        modifier = modifier
            .animateContentSize()
            .padding(8.dp)
            .clickable {
                onClick(character.id)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                modifier = Modifier.size(100.dp),
                imageRequest = ImageRequest
                    .Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .build()
            )
            CharacterDescription(
                character = character,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f)
            )
            FavoriteButton(onClick = {
                onFavoriteClick(character.id)
            }, favorite = favorite)
        }
    }
}

@Composable
fun CharacterDescription(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusColorCircle(character.status)
            Text(text = "${character.status} - ${character.species}")
        }
    }
}

@Composable
fun StatusColorCircle(status: String) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(
                color = when (status) {
                    "Dead" -> Color.Red
                    "Alive" -> Color.Green
                    else -> Color.Gray
                }, shape = CircleShape
            )
    )
}

@Composable
fun FavoriteButton(
    onClick: () -> Unit,
    favorite: Boolean
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = when {
                favorite -> Icons.Filled.Favorite
                else -> Icons.Filled.FavoriteBorder
            },
            tint = when {
                favorite -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            },
            contentDescription = null
        )
    }
}

