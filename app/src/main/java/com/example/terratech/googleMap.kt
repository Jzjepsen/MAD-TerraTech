package com.example.terratech

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Surface
import androidx.compose.ui.unit.sp
import androidx.compose.material.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import com.example.terratech.ui.theme.TerraTechTheme
import com.google.android.gms.maps.GoogleMap


class googleMap : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapView = MapView(this)
        mapView.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@googleMap, listOfTerrariums::class.java)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column() {
                        Row() {
                            IconButton(onClick = {
                                startActivity(intentHome)
                            }) {
                                Icon(Icons.Filled.Home, "home")
                            }
                            Button(onClick = {
                                finish()
                            }) {
                                Icon(Icons.Filled.ArrowBack, "back")
                                Text("  Back", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        MapViewContainer()
                    }
                }
            }
            MapScreen()
        }
    }

    @Composable
    fun MyTopBar(context: Context) {
        TopAppBar(
            title = { Text("Google Maps Overview", color = Color.White, fontSize = 18.sp) },
            backgroundColor = Color.DarkGray,
            actions = {
                IconButton(onClick = {
                    context.startActivity(Intent(context, listOfTerrariums::class.java))
                }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home")
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    (context as? Activity)?.finish() // Casting the context to Activity to call finish
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MapScreen() {
        val context = LocalContext.current // Getting the context
        Scaffold(
            topBar = { MyTopBar(context) }
        ) { paddingValues ->
            MapViewContainer(mapView, paddingValues)
        }
    }

    @Composable
    fun MapViewContainer(mapView: MapView, paddingValues: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Surface(color = Color.DarkGray, modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Phone Coordinates:\nLat: 56°08'40.8\"N\nLong: 10°11'06.5\"E",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            AndroidView(
                { mapView },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) { mapView ->
                mapView.getMapAsync { googleMap ->
                    setupGoogleMap(googleMap)
                }
            }
        }
    }


    private fun setupGoogleMap(googleMap: GoogleMap) {
        MapsInitializer.initialize(this@googleMap)
        if (ContextCompat.checkSelfPermission(
                this@googleMap,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    googleMap.addMarker(
                        MarkerOptions().position(currentLocation).title("You are here")
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this@googleMap,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    mapView.getMapAsync { googleMap ->
                        googleMap.isMyLocationEnabled = true
                        // Add additional setup if required
                    }
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}