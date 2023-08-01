package com.eugenics.gpslive.ui.compose.screens.coordinates

import android.location.Location
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eugenics.gpslive.core.LathLong
import com.eugenics.gpslive.ui.compose.screens.coordinates.components.Accuracy
import com.eugenics.gpslive.ui.compose.screens.coordinates.components.Altitude
import com.eugenics.gpslive.ui.compose.screens.coordinates.components.Point
import com.eugenics.gpslive.ui.compose.screens.coordinates.components.Speed
import com.eugenics.gpslive.ui.theme.GPSLiveTheme
import com.eugenics.gpslive.util.convertCoordinateToSting
import kotlin.math.roundToInt

@Composable
fun Coordinates(
    modifier: Modifier = Modifier,
    location: State<Location?> = mutableStateOf(null)
) {

    val lath = location.value?.latitude ?: 0.0
    val long = location.value?.longitude ?: 0.0
    val altitude = (location.value?.altitude ?: 0.0).roundToInt()
    val speed = (location.value?.speed?.toDouble() ?: 0.0).roundToInt()
    val accuracy = (location.value?.accuracy?.toDouble() ?: 0.0).roundToInt()
    val verticalAccuracy = (location.value?.verticalAccuracyMeters?.toDouble() ?: 0.0).roundToInt()

    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = lazyColumnState
    ) {
        item {
            Point(
                lath = convertCoordinateToSting(lath, LathLong.LATH),
                long = convertCoordinateToSting(long, LathLong.LONG)
            )
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Altitude(altitude = altitude)
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Speed(speed = speed)
            }
        }
        item {
            Accuracy(
                accuracy = accuracy,
                vertAccuracy = verticalAccuracy
            )
        }
    }
}

@Preview
@Composable
private fun CoordinatesPreview() {
    GPSLiveTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Coordinates()
        }
    }
}