package com.news.rickandmortyzara.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.news.rick_and_morty_zara.R

enum class NavItem(
    val navCommand: NavCommand,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector,
    @StringRes val title: Int
) {
    CHARACTERS(NavCommand.ContentType(Feature.CHARACTERS), Icons.Outlined.Face, Icons.Filled.Face, R.string.characters),
    FAVORITES(NavCommand.ContentType(Feature.FAVORITES), Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite, R.string.favorites)
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentTypeDetail(feature: Feature) :
        NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    ItemId(key = "itemId", NavType.IntType)
}