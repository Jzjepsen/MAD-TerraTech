package com.example.terratech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun TerrariumDetailsScreen(viewModel: TerrariumViewModel) {
    val terrariumData by viewModel.terrariumData.collectAsState()

    Column {
        PageHeader(title = "Terrarium Overview")
        DisplayData(
            degrees = terrariumData.temperature.toString(),
            humidity = terrariumData.humidity.toString()
        )
        // Additional content can go here...
    }
}

// Entry point of the app
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
                    TerrariumDetailsScreen(viewModel)
                }
            }
        }
    }
}

// Preview of the TerrariumDetailsScreen
@Preview(showBackground = true)
@Composable
fun TerrariumDetailsScreenPreview() {
    TerraTechTheme {
        TerrariumDetailsScreen(TerrariumViewModel())
    }
}

// Preview of the DisplayData
@Preview(showBackground = true)
@Composable
fun DisplayDataPreview() {
    TerraTechTheme {
        DisplayData(degrees = "24", humidity = "60")
    }
}