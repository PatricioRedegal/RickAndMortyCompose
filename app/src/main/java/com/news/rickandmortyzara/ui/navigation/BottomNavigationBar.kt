package com.news.rickandmortyzara.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onNavItemClick: (NavItem) -> Unit
) {
    NavigationBar(
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        bottomNavOptions.forEach { item ->
            val title = stringResource(item.title)
            val selected = currentRoute.contains(item.navCommand.feature.route)

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                selected = selected,
                icon = {
                    Icon(
                        imageVector = when {
                            selected -> item.iconFilled
                            else -> item.iconOutlined
                        },
                        contentDescription = title,
                        tint = when {
                            selected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.primary
                        }
                    )
                },
                label = {
                    Text(
                        text = title,
                        color = when {
                            selected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.primary
                        },
                        fontWeight = when {
                            selected -> FontWeight.Bold
                            else -> FontWeight.W400
                        }
                    )
                },
                onClick = { onNavItemClick(item) }
            )
        }
    }
}

