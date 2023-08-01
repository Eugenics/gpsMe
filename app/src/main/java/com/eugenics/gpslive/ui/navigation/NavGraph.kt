package com.eugenics.gpslive.ui.navigation

import android.location.Location
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.eugenics.gpslive.core.ApplicationSettings
import com.eugenics.gpslive.core.Theme
import com.eugenics.gpslive.ui.compose.screens.main.MainScreen
import com.eugenics.gpslive.ui.compose.screens.settings.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberAnimatedNavController(),
    location: State<Location?>,
    settings: State<ApplicationSettings> = mutableStateOf(ApplicationSettings.newInstance()),
    onShareClick: (location: Location?) -> Unit = {},
    onThemePick: (theme: Theme) -> Unit = {},
    onPlaceClick: () -> Unit = {}
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                navController = navController,
                location = location,
                onShareClick = onShareClick,
                onPlaceClick = onPlaceClick
            )
        }
        composable(route = Screen.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(500)
                )
            }) {
            SettingsScreen(
                onBackPressed = { navController.popBackStack() },
                settings = settings,
                onThemePick = onThemePick
            )
        }
    }
}