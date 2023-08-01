package com.eugenics.gpslive.core

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationSettings(
    val theme: Theme
) {
    companion object {
        fun newInstance(): ApplicationSettings = ApplicationSettings(theme = Theme.SYSTEM)
    }
}