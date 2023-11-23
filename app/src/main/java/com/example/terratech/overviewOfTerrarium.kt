package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


// ViewModel to handle the logic and data for the terrarium
class TerrariumViewModel : ViewModel() {
    val terrariumData = MutableStateFlow(TerrariumData(0.0, 0))

    init {
        fetchTerrariumData()
    }
    fun refreshData() {
        fetchTerrariumData() // Your existing function to fetch data
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
            .fillMaxWidth() // This ensures the Surface takes the full width
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(), // Ensures Text takes full width of the Surface
            textAlign = TextAlign.Center // Centers the text
        )
    }
}

@Composable
fun DisplayData(degrees: String, humidity: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)) // Customize the box appearance
            .padding(16.dp) // Padding inside the box
    ) {
        Column {
            Text(
                text = "Degrees: $degrees°C",
                style = TextStyle(
                    fontSize = 18.sp, // Larger font size
                    fontWeight = FontWeight.Bold // Optional: make it bold
                )
            )
            Text(
                text = "Humidity: $humidity%",
                style = TextStyle(
                    fontSize = 18.sp, // Larger font size
                    fontWeight = FontWeight.Bold // Optional: make it bold
                )
            )
        }
    }
}



// Main screen Composable function
@Composable
fun TerrariumDetailsScreen(viewModel: TerrariumViewModel, onManageTerrariumClicked : () -> Unit, onGoogleMapClicked : () -> Unit ) {
    val terrariumData by viewModel.terrariumData.collectAsState()

    Column {
        PageHeader(title = "Terrarium Overview")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(15.dp))

            DisplayData(
                degrees = terrariumData.temperature.toString(),
                humidity = terrariumData.humidity.toString()
            )

            Spacer(Modifier.width(40.dp))


            Button(onClick = { viewModel.refreshData() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Refresh")
            }
        }
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(15.dp))

            Button(onClick = onManageTerrariumClicked) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Icon Description"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Manage Terrarium")
            }
            Spacer(Modifier.width(40.dp)) // Add spacing here

            Button(onClick = onGoogleMapClicked) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Icon Description"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Location")
            }
        }
        Spacer(Modifier.width(40.dp)) // Add spacing here

        Image(
            painter = painterResource(id = R.drawable.gecko),
            contentDescription = "Terrarium Image",
            modifier = Modifier
                .height(330.dp) // Specify the height of the image
                .fillMaxWidth() // Make the image fill the maximum width
                .padding(16.dp) // Add some padding
        )
    }
}


class overviewOfTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@overviewOfTerrarium, listOfTerrariums::class.java)
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
                        val viewModel: TerrariumViewModel = viewModel()
                        TerrariumDetailsScreen(
                            viewModel,
                            onManageTerrariumClicked = { navigateToManageTerrarium() },
                            onGoogleMapClicked = { navigateToGoogleMap() }
                        )
                    }
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
