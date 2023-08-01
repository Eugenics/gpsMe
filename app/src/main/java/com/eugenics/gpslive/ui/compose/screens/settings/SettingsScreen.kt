package com.eugenics.gpslive.ui.compose.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eugenics.gpslive.R
import com.eugenics.gpslive.core.ApplicationSettings
import com.eugenics.gpslive.core.Theme
import com.eugenics.gpslive.ui.compose.screens.settings.components.CategoryRow
import com.eugenics.gpslive.ui.compose.screens.settings.components.SettingsAppBar
import com.eugenics.gpslive.ui.compose.screens.settings.components.SettingsTextRow
import com.eugenics.gpslive.ui.compose.screens.settings.components.SoftwareInfoDialog
import com.eugenics.gpslive.ui.compose.screens.settings.components.ThemeChooseDialog
import com.eugenics.gpslive.ui.compose.util.PreviewBase


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settings: State<ApplicationSettings> = mutableStateOf(ApplicationSettings.newInstance()),
    onBackPressed: () -> Unit = {},
    onThemePick: (theme: Theme) -> Unit = { _ -> }
) {
    val showThemeDialog = remember { mutableStateOf(false) }
    val showLicenseDialog = remember { mutableStateOf(false) }
    val themeName = remember { mutableStateOf(settings.value.theme) }

    if (showThemeDialog.value) {
        ThemeChooseDialog(
            currentTheme = themeName.value,
            onThemeChoose = { theme ->
                onThemePick(theme)
                themeName.value = theme
                showThemeDialog.value = false
            }
        ) {
            showThemeDialog.value = false
        }
    }

    if (showLicenseDialog.value) {
        SoftwareInfoDialog { showLicenseDialog.value = false }
    }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            SettingsAppBar(
                onBackPressed = onBackPressed
            )

            CategoryRow(categoryName = stringResource(R.string.display_settings_category))

            SettingsTextRow(
                onRowClick = { showThemeDialog.value = true },
                nameText = stringResource(R.string.theme_title_string),
                valueText = when (themeName.value) {
                    Theme.DARK -> stringResource(R.string.dark_text)
                    Theme.LIGHT -> stringResource(R.string.light_text)
                    else -> stringResource(R.string.system_text)
                }
            )

            CategoryRow(categoryName = stringResource(R.string.info))

            SettingsTextRow(
                onRowClick = { showLicenseDialog.value = true },
                nameText = stringResource(R.string.about),
                valueText = ""
            )
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
private fun SettingsScreenPreview() {
    PreviewBase {
        SettingsScreen()
    }
}