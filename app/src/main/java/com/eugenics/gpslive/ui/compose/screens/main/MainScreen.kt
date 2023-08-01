package com.eugenics.gpslive.ui.compose.screens.main

import android.content.res.Configuration
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.gpslive.ui.compose.screens.coordinates.Coordinates
import com.eugenics.gpslive.ui.compose.screens.main.components.MultiFAB
import com.eugenics.gpslive.ui.compose.util.PreviewBase
import com.eugenics.gpslive.ui.navigation.Screen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    location: State<Location?>,
    onShareClick: (location: Location?) -> Unit,
    onPlaceClick: () -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    val windowPaddingValues = remember { mutableStateOf(PaddingValues(0.dp)) }

    Scaffold(
        floatingActionButton = {
            MultiFAB(paddingValues = if (orientation == Configuration.ORIENTATION_LANDSCAPE) windowPaddingValues.value
            else PaddingValues(0.dp),
                onSharing = { onShareClick(location.value) },
                onSettings = { navController.navigate(Screen.Settings.route) },
                onPlace = onPlaceClick
            )
        }, floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        windowPaddingValues.value = paddingValues
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Coordinates(
                modifier = Modifier.align(Alignment.TopCenter), location = location
            )
        }
    }
}

@Composable
@Preview(apiLevel = 27, name = "basePreview_27API")
private fun MainScreenPreview() {
    val locationState = remember { mutableStateOf(null) }
    PreviewBase {
        MainScreen(location = locationState, onShareClick = {}, onPlaceClick = {})
    }
}