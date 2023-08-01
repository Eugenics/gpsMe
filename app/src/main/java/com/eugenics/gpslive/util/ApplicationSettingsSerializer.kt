package com.eugenics.gpslive.util

import android.util.Log
import androidx.datastore.core.Serializer
import com.eugenics.gpslive.core.ApplicationSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ApplicationSettingsSerializer : Serializer<ApplicationSettings> {
    override val defaultValue: ApplicationSettings
        get() = ApplicationSettings.newInstance()

    override suspend fun readFrom(input: InputStream): ApplicationSettings =
        try {
            Json.decodeFromString(
                ApplicationSettings.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
            defaultValue
        }

    override suspend fun writeTo(t: ApplicationSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            try {
                output.write(
                    Json.encodeToString(
                        ApplicationSettings.serializer(),
                        t
                    ).encodeToByteArray()
                )
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    companion object {
        private const val TAG = "ApplicationSettingsSerializer"
    }
}