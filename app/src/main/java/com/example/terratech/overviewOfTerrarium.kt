package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.terratech.ui.theme.TerraTechTheme
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.material3.Icon


// ViewModel to handle the logic and data for the terrarium
class TerrariumViewModel : ViewModel() {
    val terrariumData = MutableStateFlow(TerrariumData(0.0, 0))

    init {
        fetchTerrariumData()
    }

    private fun fetchTerrariumData() {
        // Fetch data from your API and update the terrariumData
    }
}

// Data class to represent the terrarium data
data class TerrariumData(val temperature: Double, val humidity: Int)

// Composable function to display the header
@Composable
fun PageHeader(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

// Composable function to display the terrarium data
@Composable
fun DisplayData(degrees: String, humidity: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Degrees: $degrees°C",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Humidity: $humidity%",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Main screen Composable function
@Composable
fun TerrariumDetailsScreen(viewModel: TerrariumViewModel, onManageTerrariumClicked : () -> Unit, onGoogleMapClicked : () -> Unit ) {
    val terrariumData by viewModel.terrariumData.collectAsState()

    Column {
        PageHeader(title = "Terrarium Overview")
        DisplayData(
            degrees = terrariumData.temperature.toString(),
            humidity = terrariumData.humidity.toString()
        )
        Button(onClick = onManageTerrariumClicked) {
            Icon(
                imageVector = Icons.Filled.Settings, // Replace with your desired icon
                contentDescription = "Icon Description"
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing)) // Adds spacing between icon and text
            Text("Manage Terrarium")
        }
        Button(onClick = onGoogleMapClicked) {
            Icon(
                imageVector = Icons.Filled.LocationOn, // Replace with your desired icon
                contentDescription = "Icon Description"
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing)) // Adds spacing between icon and text
            Text("Location")
        }
        // Additional content can go here...
    }
}


class overviewOfTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TerrariumViewModel = viewModel()
                    TerrariumDetailsScreen(
                        viewModel,
                        onManageTerrariumClicked = { navigateToManageTerrarium() },
                        onGoogleMapClicked = { navigateToGoogleMap() } // Add this
                    )
                }
            }
        }
    }

    private fun navigateToManageTerrarium() {
        val intent = Intent(this, manageTerrarium::class.java)
        startActivity(intent)
    }

    private fun navigateToGoogleMap() {
        val intent = Intent(this, googleMap::class.java)
        startActivity(intent)
    }
}
