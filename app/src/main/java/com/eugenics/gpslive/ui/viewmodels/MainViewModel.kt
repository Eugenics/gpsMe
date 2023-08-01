package com.eugenics.gpslive.ui.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugenics.gpslive.core.ApplicationSettings
import com.eugenics.gpslive.util.DataStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var dataStore: DataStore<ApplicationSettings>? = null

    private val _settings: MutableStateFlow<ApplicationSettings> =
        MutableStateFlow(ApplicationSettings.newInstance())
    val settings: StateFlow<ApplicationSettings> = _settings

    fun getDataStore(path: String) {
        if (dataStore == null) {
            dataStore = DataStoreFactory.create(path = path)
            collectSettings()
        }
    }

    fun updateSettings(applicationSettings: ApplicationSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore?.updateData {
                applicationSettings
            }
        }
    }

    private fun collectSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore?.data?.collect { applicationSettings ->
                _settings.value = applicationSettings
            }
        }
    }
}