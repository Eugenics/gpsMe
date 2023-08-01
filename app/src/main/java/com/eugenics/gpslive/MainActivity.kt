package com.eugenics.gpslive

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import app.organicmaps.api.DownloadDialog
import app.organicmaps.api.MapRequest
import app.organicmaps.api.Point
import com.eugenics.gpslive.core.ApplicationSettings
import com.eugenics.gpslive.core.LathLong
import com.eugenics.gpslive.core.Theme
import com.eugenics.gpslive.ui.compose.screens.warning.WarningScreen
import com.eugenics.gpslive.ui.navigation.NavGraph
import com.eugenics.gpslive.ui.theme.GPSLiveTheme
import com.eugenics.gpslive.ui.viewmodels.MainViewModel
import com.eugenics.gpslive.util.convertCoordinateToSting
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private var permissionsGranted: MutableState<Boolean> = mutableStateOf(false)

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var locationClient: FusedLocationProviderClient
    private val currentLocation: MutableState<Location?> = mutableStateOf(null)
    private val gpsStatusState: MutableState<Boolean> = mutableStateOf(true)

    private val checkPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            for (permission in permissions) {
                if (!permission.value) {
                    Log.d(TAG, "Requested permission ${permission.key} denied!")
                }
            }
            permissionsGranted.value = permissions.filter { !it.value }.isEmpty()
        }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            currentLocation.value = result.lastLocation
            Log.d(TAG, "Last location: ${result.lastLocation}")
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        checkPermissions()
        mainViewModel.getDataStore(applicationInfo.dataDir)

        setContent {
            LaunchedEffect(null) {
                while (true) {
                    checkGPS()
                    delay(1000L)
                }
            }

            val settings = mainViewModel.settings.collectAsState()

            val isDark = when (settings.value.theme) {
                Theme.DARK -> true
                Theme.SYSTEM -> resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES
                else -> false
            }

            GPSLiveTheme(darkTheme = isDark) {
                val permission = remember { permissionsGranted }
                val gpsStatus = remember { gpsStatusState }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 5.dp
                ) {
                    if (permission.value) {
                        if (gpsStatus.value) {
                            locationClient = LocationServices.getFusedLocationProviderClient(this)
                            getLocation(locationClient = locationClient)


                            val navController = rememberAnimatedNavController()
                            NavGraph(navController = navController,
                                location = currentLocation,
                                onShareClick = { location -> shareLocation(location) },
                                settings = settings,
                                onThemePick = { theme ->
                                    mainViewModel.updateSettings(
                                        ApplicationSettings(theme = theme)
                                    )
                                },
                                onPlaceClick = {
                                    openLocationInOrganicsMaps(location = currentLocation.value)
                                })
                        } else {
                            WarningScreen(text = getString(R.string.gps_down_warning))
                        }
                    } else {
                        WarningScreen(text = getString(R.string.permissions_grant_warning))
                    }
                }
            }
        }
    }

    private fun getLocation(locationClient: FusedLocationProviderClient) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermissions()
            return
        }
        locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun checkPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                permissionsGranted.value = true
            }

            else -> {
                checkPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }
    }

    private fun checkGPS() {
        gpsStatusState.value =
            (this.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
    }

    private fun shareLocation(location: Location?) {
        location?.let {
            val lath = convertCoordinateToSting(it.latitude, LathLong.LATH)
            val long = convertCoordinateToSting(it.longitude, LathLong.LONG)
            val altitude = it.altitude.roundToInt()

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT, "$lath\n$long\nAlt: $altitude m"
                )
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.share_location))
            startActivity(shareIntent)
        }
    }

    private fun openLocationInOrganicsMaps(location: Location?) {
        location?.let {
            val lath = convertCoordinateToSting(it.latitude, LathLong.LATH)
            val long = convertCoordinateToSting(it.longitude, LathLong.LONG)
            val mapPoints = mutableListOf<Point>().also {
                it.add(
                    Point(
                        location.latitude,
                        location.longitude,
                        "${getString(R.string.my_location)}\n$lath\n$long"
                    )
                )
            }
            val intent = MapRequest().apply {
                setAppName(getString(R.string.app_name))
                setPoints(mapPoints)
                setZoomLevel(15.0)
                setPickPointMode(false)
            }.toIntent()

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, e.toString())
                DownloadDialog(this).show()
            }
        }
    }

    companion object {
        private const val TAG = "Main Activity"

        private val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500L).build()
    }
}