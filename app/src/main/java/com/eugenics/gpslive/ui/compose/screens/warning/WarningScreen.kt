package com.eugenics.gpslive.ui.compose.screens.warning

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eugenics.gpslive.R
import com.eugenics.gpslive.ui.compose.util.PreviewBase

@Composable
fun WarningScreen(text: String = "Warning text") {
    val orientation = LocalConfiguration.current.orientation
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.warning_background),
            contentDescription = null,
            contentScale = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ContentScale.FillHeight
            } else {
                ContentScale.FillBounds
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.Black
            )
            Text(text = text, color = Color.Black)
        }
    }
}

@Preview
@Composable
private fun WarningScreenPreview() {
    PreviewBase { WarningScreen() }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=480")
@Composable
private fun WarningScreenLandscapePreview() {
    PreviewBase { WarningScreen() }
}