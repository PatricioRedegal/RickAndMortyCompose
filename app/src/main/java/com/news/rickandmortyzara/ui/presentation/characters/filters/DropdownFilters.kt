package com.news.rickandmortyzara.ui.presentation.characters.filters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.news.rick_and_morty_zara.R
import com.news.rickandmortyzara.ui.presentation.characters.charactersList.StatusColorCircle

@Composable
fun DropdownFilters(
    expanded: Boolean = false,
    filterStatus: String,
    onClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        Text(
            text = stringResource(R.string.filter_by_status),
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        CustomDropDownMenuItem(
            status = "Dead",
            checked = "Dead" == filterStatus,
            text = stringResource(R.string.dead),
            onClick = onClick
        )
        CustomDropDownMenuItem(
            status = "Alive",
            checked = "Alive" == filterStatus,
            text = stringResource(R.string.alive),
            onClick = onClick
        )
        CustomDropDownMenuItem(
            status = "Unknown",
            checked = "Unknown" == filterStatus,
            text = stringResource(R.string.unknown),
            onClick = onClick
        )
    }
}

@Composable
private fun CustomDropDownMenuItem(
    status: String,
    checked: Boolean,
    text: String,
    onClick: (String) -> Unit
) {
    DropdownMenuItem(
        onClick = {
            onClick( if (checked) "" else status)
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusColorCircle(status = status)
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = text,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if(checked) {
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

