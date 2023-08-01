package com.eugenics.gpslive.ui.compose.screens.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.eugenics.gpslive.ui.compose.util.PreviewBase

@Composable
fun MultiFAB(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onSharing: () -> Unit = {},
    onSettings: () -> Unit = {},
    onPlace: () -> Unit = {}
) {
    val expand = rememberSaveable { mutableStateOf(false) }
    val fabIcon = remember { mutableStateOf(Icons.Filled.Menu) }
    val rotation = animateFloatAsState(
        targetValue = if (expand.value) 360f else 0f,
        label = "fabRotation"
    )

    LaunchedEffect(expand.value) {
        if (expand.value) {
            fabIcon.value = Icons.Filled.Home
        } else {
            fabIcon.value = Icons.Filled.Menu
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = expand.value,
            enter = slideIn(
                tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )
            ) { fullSize ->
                IntOffset(0, fullSize.height / 2)
            },
            exit = slideOut(
                tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )
            ) { fullSize ->

                IntOffset(0, fullSize.height / 2)
            }
        ) {
            Column(
                modifier = Modifier.wrapContentSize()
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        onPlace()
                        expand.value = !expand.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation.value)
                    )
                }

                SmallFloatingActionButton(
                    onClick = {
                        onSettings()
                        expand.value = !expand.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation.value)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.Bottom
        ) {

            AnimatedVisibility(
                visible = expand.value,
                enter = slideIn(
                    tween(
                        durationMillis = 100,
                        easing = LinearOutSlowInEasing
                    )
                ) { fullSize ->
                    IntOffset(fullSize.height / 2, 0)
                },
                exit = slideOut(
                    tween(
                        durationMillis = 100,
                        easing = LinearOutSlowInEasing
                    )
                ) { fullSize ->
                    IntOffset(fullSize.height / 2, 0)
                }
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        onSharing()
                        expand.value = !expand.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation.value)
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                onClick = { expand.value = !expand.value }
            ) {
                Icon(
                    imageVector = fabIcon.value,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation.value)
                )
            }
        }
    }
}

@Composable
@Preview
private fun MultiFABPreview() {
    PreviewBase {
        Scaffold(
            floatingActionButton = { MultiFAB() }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Text(
                    text = "Floating Action Button Test Screen"
                )
            }
        }
    }
}