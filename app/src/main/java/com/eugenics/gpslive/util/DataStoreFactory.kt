package com.eugenics.gpslive.util

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.eugenics.gpslive.core.ApplicationSettings
import java.io.File

object DataStoreFactory {
    private val serializer = ApplicationSettingsSerializer()

    fun create(path: String): DataStore<ApplicationSettings> = DataStoreFactory.create(
        serializer = serializer,
        produceFile = {
            File(path, "datastore/settings_datastore.pd")
        }
    )
}