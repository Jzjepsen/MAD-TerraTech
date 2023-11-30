package com.example.terratech

import android.Manifest
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
import com.example.terratech.ui.theme.TerraTechTheme


class googleMap : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

        }
    }

    @Composable
    fun MapViewContainer() {
        val mapView = remember {
            MapView(this).apply {
                onCreate(Bundle())
                this.getMapAsync { googleMap ->
                    MapsInitializer.initialize(this@googleMap)
                    if (ContextCompat.checkSelfPermission(this@googleMap,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val currentLocation = LatLng(location.latitude, location.longitude)
                                googleMap.addMarker(MarkerOptions().position(currentLocation).title("You are here"))
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                            }
                        }
                    } else {
                        ActivityCompat.requestPermissions(this@googleMap,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                    }
                }
            }
        }

        Column {
            // Adding title text at the top of the screen
            TopAppBar(
                title = { Text("Google Maps Overview", color = Color.White, fontSize = 18.sp) },
                backgroundColor = Color.DarkGray,
                modifier = Modifier.padding(top = 16.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center,
                content = {
                    Surface(color = Color.DarkGray, modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Terrarium Coordinates:\nLat: 56°08'40.8\"N\nLong: 10°11'06.5\"E",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            )

            Box(modifier = Modifier.fillMaxSize().offset(y = (-100).dp), contentAlignment = Alignment.Center) {
                AndroidView({ mapView }, modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight(0.4f))
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
