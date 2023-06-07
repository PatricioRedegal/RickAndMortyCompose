package com.news.rickandmortyzara.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.news.rickandmortyzara.ui.navigation.NavItem
import com.news.rickandmortyzara.ui.navigation.navigatePoppingUpToStartDestination

@Composable
fun rememberRickAndMortyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
): RickAndMortyAppState = remember(scaffoldState, navController) {
    RickAndMortyAppState(scaffoldState, navController)
}

class RickAndMortyAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
) {
    companion object {
        val BOTTOM_NAV_OPTIONS = listOf(NavItem.CHARACTERS, NavItem.FAVORITES)
    }

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    var showBottomNavigation by mutableStateOf(true)
    var topBarTitle by mutableStateOf(0)

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onNavItemClick(navItem: NavItem) {
        topBarTitle = navItem.title
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
        showBottomNavigation = BOTTOM_NAV_OPTIONS.any { navItem.navCommand.route.contains(it.navCommand.feature.route) }
    }
}