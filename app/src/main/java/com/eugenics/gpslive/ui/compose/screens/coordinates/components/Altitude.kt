package com.eugenics.gpslive.ui.compose.screens.coordinates.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eugenics.gpslive.R

@Composable
fun Altitude(
    modifier: Modifier = Modifier,
    altitude: Int
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(5.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.altitude),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(5.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "$altitude ${stringResource(R.string.m)}",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
                    .fillMaxWidth()
            )
        }
    }
}