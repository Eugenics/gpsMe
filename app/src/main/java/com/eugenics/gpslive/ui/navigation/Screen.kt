package com.eugenics.gpslive.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen(route = "main")
    object Settings : Screen(route = "settings")
}
